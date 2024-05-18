import {Pipe, PipeTransform} from '@angular/core';
import {TargetModel} from "../models/target.model";
import {EventEnum} from "../models/event.enum";
import {ImpactModel} from "../models/impact.model";
import {ZoneEnum} from "../models/zone.enum";

enum PenaltyType {
  TIME_RAN_OUT = 50,
  DEPARTURE_STEAL = 50,
  ARMED_BEFORE_COUNTDOWN = 50,
  BAD_ARROW_EXTRACTION = 50,
  TARGET_SHEET_NOT_TOUCHED = 50,
  SHOTS_TOO_CLOSE = 50
}

class SuperBiathlonParameters {
  static readonly MIN_NUMBER_VALID_IMPACTS = 3;
  static readonly NUMBER_OF_IMPACTS = 5;
  static readonly NUMBER_OF_IMPACTS_QUALIFICATION = 5;

  static readonly BASE_TIME_PENALTY = 5;
  static readonly BASE_TIME_BONUS = 3;
  static readonly BASE_SCORE = 5000;
  static readonly REFERENCE_TIME = 90;
}


const REFERENCE_TIME = 90; // 1 min 30 s
const TIME_BONUS = 3;
const PENALTY_TIME = 5;
const MAX_SHOTS_PENALTY = 5;


@Pipe({
  name: 'result',
  standalone: true
})
export class ResultPipe implements PipeTransform {

  transform(value: TargetModel): number {
    return this.total(value);
  }


  total(target: TargetModel): number {
    if (target.event === EventEnum.PRECISION) {
      return this.calculateScorePrecision(target);
    } else if (target.event === EventEnum.BIATHLON) {
      return this.calculateScoreBiathlon(target);
    } else if (target.event === EventEnum.SUPER_BIATHLON) {
      return this.getScoreSuperBiathlon(target);
    }
    return 0;
  }



  calculateScorePrecision(target: TargetModel): number {
    let score = 0;
    let impactCount = 0;
    const impactMap = new Map<ZoneEnum, ImpactModel[]>();

    // Calculer le score initial en prenant les 10 meilleurs impacts
    for (const impact of target.impacts.sort((a, b) => b.score - a.score)) {
      if (impactCount >= 10) {
        break;
      }
      score += impact.score;
      impactCount++;

      // Grouper les impacts par visuel pour la règle des 2 meilleurs impacts par visuel
      if (!impactMap.has(impact.zone)) {
        impactMap.set(impact.zone, []);
      }
      impactMap.get(impact.zone)?.push(impact);
    }

    // Appliquer la règle des 2 meilleurs impacts par visuel
    for (const impacts of impactMap.values()) {
      if (impacts.length > 2) {
        const excessScore = impacts.slice(2).reduce((sum, impact) => sum + impact.score, 0);
        score -= excessScore;
      }
    }

    // Appliquer les pénalités
    if (target.timeRanOut) {
      const worstImpact = target.impacts.sort((a, b) => a.score - b.score)[0];
      score -= worstImpact.score;
    }
    if (target.impacts.length > 10) {
      const excessScore = target.impacts.slice(10).reduce((sum, impact) => sum + impact.score, 0);
      score -= excessScore;
    }
    score -= 50 * (target.shotsTooCloseCount + target.badArrowExtractionsCount + target.targetSheetNotTouchedCount + (target.departureSteal ? 1 : 0) + (target.armedBeforeCountdown ? 1 : 0));

    return Math.max(0, score); // Pas de score négatif
  }



  calculateScoreBiathlon(target: TargetModel): number {
    let impacts = [...target.impacts.sort((a, b) => b.score - a.score)];
    // En cas de dépassement du temps, le plus mauvais tir sera refusé.
    if (target.time > 600000) {
      console.log('pop impact time ran out');
      impacts.pop();
    }

    // Si plus de 3 impacts sont relevés sur le plastron, le score sera amputé du ou des meilleurs impacts
    // supplémentaires.
    // Si plus de 3 tirs (déclenchements) sont comptabilisés, le score sera amputé du ou des meilleurs
    // impacts supplémentaires.
    if (target.impacts.length > 3) {
      let impactsByZone = this.getImpactsByZone(impacts);
      let numberToRemove = target.impacts.length - 3;
      while (numberToRemove > 0) {
        debugger
        if (this.hasImpactOnSameVisual(impactsByZone)) {
          //check the impact with highest score is in the same visual
          impacts.forEach((impact) => {
            if (impactsByZone[impact.zone].length > 1 && numberToRemove > 0) {
              impacts.shift();
              impactsByZone[impact.zone].shift();
              numberToRemove--;
            }
          });
        } else {
          impacts.shift();
          numberToRemove--;
        }
      }
    }

    //Si plus de 1 impact est relevé sur le même visuel, seul le meilleur impact sera retenu.
    let impactsByZone = this.getImpactsByZone(impacts);
    while (this.hasImpactOnSameVisual(impactsByZone)) {

      Object.values(impactsByZone).forEach((impacts) => {
        while (impacts.length > 1) {
          console.log('pop impact meme visuel');
          impacts.pop();
        }
      }
      );

      // impacts.forEach((impact) => {
      //   while (impactsByZone[impact.zone].length > 1) {
      //     console.log('pop impact meme visuel');
      //     impacts.pop();
      //     impactsByZone[impact.zone].pop();
      //   }
      // });
    }
    impacts = [];
    Object.values(impactsByZone).forEach((listImpacts) => {
      listImpacts.forEach((impact) => {
        impacts.push(impact);
      });
    });


    //Calculer le score
    let score = impacts.reduce((sum, impact) => sum + impact.score, 0);

    // Appliquer les pénalités
    let penalties = 0;
    if (target.departureSteal) {
      penalties += PenaltyType.DEPARTURE_STEAL;
    }
    if (target.armedBeforeCountdown) {
      penalties += PenaltyType.ARMED_BEFORE_COUNTDOWN;
    }
    if (target.shotsTooCloseCount) {
      penalties += PenaltyType.SHOTS_TOO_CLOSE;
    }
    penalties += target.badArrowExtractionsCount * PenaltyType.BAD_ARROW_EXTRACTION;
    penalties += target.targetSheetNotTouchedCount * PenaltyType.TARGET_SHEET_NOT_TOUCHED;
    // Time from milliseconds to seconds
    let time = (target.time - (target.time % 1000)) / 1000;
    return this.getScoreBiathlon(time, score, penalties);
  }

  getScoreBiathlon(time:number,score:number,penalties:number):number{
    console.log('getScoreBiathlon',time,score,penalties);
    let timeScore = Math.max(0, (score - (time * 2)) * 3);
    return Math.max(0, timeScore - penalties);
  }

    getImpactsByZone(impacts: ImpactModel[]): { [key in ZoneEnum]: ImpactModel[] } {
    const impactsByVisual: { [key in ZoneEnum]: ImpactModel[] } = {
      [ZoneEnum.TOP_LEFT]: [],
      [ZoneEnum.TOP_RIGHT]: [],
      [ZoneEnum.BOTTOM_LEFT]: [],
      [ZoneEnum.BOTTOM_RIGHT]: [],
      [ZoneEnum.CENTER]: [],
      [ZoneEnum.UNDEFINED]: []
    };
    for (const impact of impacts) {
      if (!impactsByVisual[impact.zone]) {
        impactsByVisual[impact.zone] = [];
      }
      impactsByVisual[impact.zone].push(impact);
    }
    for (const impacts of Object.values(impactsByVisual)) {
      impacts.sort((a, b) => b.score - a.score);
    }
    return impactsByVisual;
  }


  getScoreSuperBiathlon(target: TargetModel): number {

    let timePenalty = this.getTimePenalty(target);
    let timeBonus = 0;
    const isQualif = false;
    let motifsDisqualification:string[] = [];
    let time = (target.time-(target.time%1000))/1000;
    console.log("Time : ", time);
    if (!isQualif) {
      if (target.time > 600000) {
        motifsDisqualification.push("Temps supérieur à 10 minutes");
      } else {
        // Le compétiteur doit effectuer ses 5 tirs dans la cible
        if (target.impacts.length === SuperBiathlonParameters.NUMBER_OF_IMPACTS) {
          if (!this.hasHorsPlastron(target)) {
            // Son parcours est validé si et seulement si tous les visuels comprennent au maximum 1 impact
            if (!this.has1ImpactMaxPerZone(target)) {
              motifsDisqualification.push("Plus d'un impact par visuel");
            }
            // 3 visuels ont un impact compris dans les zones des (570 à 471). (Contrat cible)
            if (!this.has3ImpactsInContrat(target)) {
              motifsDisqualification.push("Nombre d'impacts dans le contrat insuffisant");
            }

            timeBonus = ResultPipe.getTimeBonus(this.getValidImpactsSuperBiathlon(target).length);
          } else {
            motifsDisqualification.push("Hors plastron");
          }
        } else {
          if (target.impacts.length < SuperBiathlonParameters.NUMBER_OF_IMPACTS) {
            motifsDisqualification.push("Nombre de tirs insuffisant");
          } else {
            motifsDisqualification.push("Nombre de tirs trop élevé");
          }
        }
      }

    }
    if (motifsDisqualification.length > 0) {
      console.log("Disqualification pour les motifs suivants : ", motifsDisqualification);
      return 0;
    }
    console.log(`this.calculateScoreSuperBiathlon(${time} + ${timePenalty} - ${timeBonus})`, this.calculateScoreSuperBiathlon(time + timePenalty - timeBonus));
    return this.calculateScoreSuperBiathlon(time + timePenalty - timeBonus);
  }

  private static getTimeBonus(numberOfValidImpacts: number) {
    console.log('getTimeBonus, numberOfValidImpacts : ', numberOfValidImpacts)
    return (numberOfValidImpacts - SuperBiathlonParameters.MIN_NUMBER_VALID_IMPACTS) * SuperBiathlonParameters.BASE_TIME_BONUS;
  }

  private getTimePenalty(target: TargetModel) {
    return (
      target.shotsTooCloseCount +
      target.badArrowExtractionsCount +
      target.targetSheetNotTouchedCount +
      (target.departureSteal ? 1 : 0) +
      (target.armedBeforeCountdown ? 1 : 0)
    ) * SuperBiathlonParameters.BASE_TIME_PENALTY;
  }

  has1ImpactMaxPerZone(target: TargetModel) {
    return this.hasOnlyOneImpactPerZone(target.impacts.filter((impact)=>impact.score));
  }

  has3ImpactsInContrat(target: TargetModel) {
    let MIN_NUMBER_VALID_IMPACTS = 3;
    let validImpacts = this.getValidImpactsSuperBiathlon(target);
    let numberOfValidImpacts = validImpacts.length;
    return numberOfValidImpacts >= MIN_NUMBER_VALID_IMPACTS
  }

  hasHorsPlastron(target: TargetModel) {
    return target.impacts.some((impact) => impact.zone === ZoneEnum.UNDEFINED);
  }

  calculateScoreSuperBiathlon(time:number):number {
    const referenceTime = 90;
    console.log(`Math.max(0, 5000 + 3 * (${referenceTime} - ${time}))`, Math.max(0, 5000 + 3 * (referenceTime - time)));
    return Math.max(0, 5000 + 3 * (referenceTime - time));
  }

  getValidImpactsSuperBiathlon(target: TargetModel): ImpactModel[] {
    return target.impacts.filter((impact) => impact.score >= 471);
  }

  private hasOnlyOneImpactPerZone(validImpacts: ImpactModel[]) {
    let zones: {
      [key in ZoneEnum]: ImpactModel[];
    } = {
      [ZoneEnum.TOP_LEFT]: [],
      [ZoneEnum.TOP_RIGHT]: [],
      [ZoneEnum.BOTTOM_LEFT]: [],
      [ZoneEnum.BOTTOM_RIGHT]: [],
      [ZoneEnum.CENTER]: [],
      [ZoneEnum.UNDEFINED]: []
    };
    validImpacts.forEach((impact) => {
      zones[impact.zone].push(impact);
    });
    return Object.values(zones).every((zone) => zone.length <= 1);
  }

  private hasImpactOnSameVisual(impactsByZone: {
    TOP_LEFT: ImpactModel[];
    TOP_RIGHT: ImpactModel[];
    BOTTOM_LEFT: ImpactModel[];
    BOTTOM_RIGHT: ImpactModel[];
    CENTER: ImpactModel[];
    UNDEFINED?: ImpactModel[]
  }) {
    delete impactsByZone.UNDEFINED;
    return Object.values(impactsByZone).some((zone) => zone.filter((impact) => impact.score).length > 1);
  }
}
