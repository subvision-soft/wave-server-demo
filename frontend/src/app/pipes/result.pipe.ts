import { Pipe, PipeTransform } from '@angular/core';
import {TargetModel} from "../models/target.model";
import {EventEnum} from "../models/event.enum";
import {ImpactModel} from "../models/impact.model";

@Pipe({
  name: 'result',
  standalone: true
})
export class ResultPipe implements PipeTransform {

  private time: number = 0;

  transform(value: TargetModel): number {
    return this.total(value);
  }






  total(target:TargetModel): number {
    if (this.tirsValides.length === 0) return 0;
    let impacts = this.tirsValides(target);

    if (target.event === EventEnum.PRECISION) {
      return impacts.reduce((acc, impact) => acc + impact.score, 0);
    } else if (target.event === EventEnum.BIATHLON) {
      const number =
        (impacts.reduce((acc, impact) => acc + impact.score, 0) -
          this.time * 2) *
        3;
      return number > 0 ? number : 0;
    } else if (target.event === EventEnum.SUPER_BIATHLON) {
      const contrats = impacts.filter(
        (impact) => impact.score >= 471
      ).length;
      const capitalPoints = 5000;
      const tempsReference = 90;
      if (contrats > 3) {
        const time1 = this.time - (contrats - 3) * 3;
        const number = capitalPoints + 3 * (tempsReference - time1);
        return number > 0 ? number : 0;
      }
    }
    return 0;
  }

  tirsValides(target:TargetModel): ImpactModel[] {
    let impacts = target.impacts;
    if (impacts.length === 0) return [];
    if (target.event === EventEnum.PRECISION) {
      //On garde les 10 moins bons impacts
      impacts = impacts
        .sort((a, b) => a.score - b.score)
        .slice(0, 10);

      let zones: any = {};
      zones = impacts.reduce((acc, impact) => {
        const zone = zones[impact.zone] || [];
        zone.push(impact);
        zones[impact.zone] = zone;
        return zones;
      });
      impacts = [];
      //On garde les 2 meilleurs impacts de chaque visuels
      Object.keys(zones).map((zone) => {
        impacts.push(
          ...zones[zone]
            .sort((a: ImpactModel, b: ImpactModel) => b.score - a.score)
            .slice(0, 2)
        );
      });
      return impacts;
    } else if (target.event === EventEnum.BIATHLON) {
      //On garde les 3 moins bons impacts
      impacts = impacts.sort((a, b) => a.score - b.score).slice(0, 3);
      let zones: any = {};
      zones = impacts.reduce((acc, impact) => {
        const zone = zones[impact.zone] || [];
        zone.push(impact);
        zones[impact.zone] = zone;
        return zones;
      });
      impacts = [];
      //On garde le meilleur impact de chaque visuels
      Object.keys(zones).map((zone) => {
        impacts.push(
          ...zones[zone]
            .sort((a: ImpactModel, b: ImpactModel) => b.score - a.score)
            .slice(0, 1)
        );
      });
      return impacts;
    } else if (target.event === EventEnum.SUPER_BIATHLON) {
      return impacts;
    }
    return [];
  }

}
