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

  private time: number = 0;

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
    let score = 0;
    let bestImpacts: ImpactModel[] = [];

    // Calculer le score pour chaque impact et garder les 3 meilleurs
    for (const impact of target.impacts) {
      score += impact.score;
      if (bestImpacts.length < 3) {
        bestImpacts.push(impact);
      } else if (impact.score > bestImpacts[0].score) {
        bestImpacts[0] = impact;
        bestImpacts.sort((a, b) => a.score - b.score);
      }
    }

    // Retirer les impacts supplémentaires si plus de 3 impacts sur le plastron ou plus de 3 tirs
    if (target.shotsTooCloseCount > 3 || target.impacts.length > 3) {
      for (const impact of bestImpacts.slice(3)) {
        score -= impact.score;
      }
    }

    // Retirer les impacts supplémentaires si plus d'un impact sur le même visuel
    const impactsByVisual: { [key in ZoneEnum]: ImpactModel[] } = {
      [ZoneEnum.TOP_LEFT]: [],
      [ZoneEnum.TOP_RIGHT]: [],
      [ZoneEnum.BOTTOM_LEFT]: [],
      [ZoneEnum.BOTTOM_RIGHT]: [],
      [ZoneEnum.CENTER]: [],
      [ZoneEnum.UNDEFINED]: []
    };
    for (const impact of target.impacts) {
      if (!impactsByVisual[impact.zone]) {
        impactsByVisual[impact.zone] = [];
      }
      impactsByVisual[impact.zone].push(impact);
    }
    for (const impacts of Object.values(impactsByVisual)) {
      if (impacts.length > 1) {
        for (const impact of impacts.slice(1)) {
          score -= impact.score;
        }
      }
    }

    // Appliquer les pénalités
    let penalties = 0;
    if (target.time > 600000) {
      penalties += PenaltyType.TIME_RAN_OUT;
    }
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
    console.log(`Math.max(0, ${score} - (${time} * 2) * 3)`, Math.max(0, score - (time * 2) * 3));
    // Calculer le score final
    const timeScore = Math.max(0, (score - (time * 2)) * 3);
    const finalScore = Math.max(0, timeScore - penalties);

    return finalScore;
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
}
