import {EventEnum} from "./event.enum";
import {ImpactModel} from "./impact.model";

export interface TargetModel {
   time:number;
   date:Date;
   competitionId:number;
   competitorId:number;
   teamId:number;
   event:EventEnum;
   id: number;
   pictureId: string;
    impacts: ImpactModel[];
}
