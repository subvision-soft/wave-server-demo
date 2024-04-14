import {CompetitorModel} from "./competitor.model";

export interface CompetitionModel {
  id:number;
  date : Date;
  description : string;
  name : string;
  competitors: CompetitorModel[];
}
