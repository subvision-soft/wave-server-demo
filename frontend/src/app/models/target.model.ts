import {EventEnum} from "./event.enum";
import {ImpactModel} from "./impact.model";
import {CompetitorModel} from "./competitor.model";
import {StageEnum} from "./stage.enum";

export interface TargetModel {
  id: number;
  pictureId: number;
  competitionId: number;
  impacts: ImpactModel[];
  event: EventEnum;
  totalScore: number;
  time: number;
  date: Date;
  competitor: CompetitorModel;
  stage:StageEnum;
  user: string;
  image: string;
  shotsTooCloseCount: number;
  badArrowExtractionsCount: number;
  targetSheetNotTouchedCount: number;
  departureSteal: boolean;
  armedBeforeCountdown: boolean;
  timeRanOut: boolean;
}
