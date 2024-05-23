import {Injectable} from "@angular/core";
import {CompetitionModel} from "../models/competition.model";
import {Paths} from "../statics/Paths";
import {TargetModel} from "../models/target.model";
import {CompetitorModel} from "../models/competitor.model";
import {callFetch} from "../statics/Fetch";

@Injectable({
  providedIn: 'root'

})
export class CompetitionsService {
  constructor() {

  }


  getAll(): Promise<CompetitionModel[]> {
    return callFetch<CompetitionModel[]>(`${Paths.API_ENDPOINT}/competitions`)
  }


  getCompetitionTargetsSSE(competitionId:number): EventSource {
    return new EventSource(`${Paths.API_ENDPOINT}/competitions/${competitionId}/targets/sse`);
  }
  create(data: CompetitionModel) {
    return callFetch<CompetitionModel>(`${Paths.API_ENDPOINT}/competitions`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    })
  }

  delete(id: number) {
    return callFetch(`${Paths.API_ENDPOINT}/competitions/${id}`, {
      method: 'DELETE'
    })
  }

  get(id: number) {
    return callFetch<CompetitionModel>(`${Paths.API_ENDPOINT}/competitions/${id}`)
  }

  getCompetitors(id: number):Promise<CompetitorModel[]> {
    return callFetch<CompetitorModel[]>(`${Paths.API_ENDPOINT}/competitions/${id}/competitors`);
  }

  addCompetitor(competitionId: number, competitorId: number) {
    return callFetch<CompetitorModel>(`${Paths.API_ENDPOINT}/competitions/${competitionId}/competitors/${competitorId}`, {
      method: 'POST'
    })
  }

  removeCompetitor(competitionId: number, competitorId: number) {
    return callFetch(`${Paths.API_ENDPOINT}/competitions/${competitionId}/competitors/${competitorId}`, {
      method: 'DELETE'
    })
  }

  getTargets(competitionId: number): Promise<TargetModel[]>{
    return callFetch<TargetModel[]>(`${Paths.API_ENDPOINT}/competitions/${competitionId}/targets`);
  }

  getTarget(competitionId: number, targetId: number): Promise<TargetModel>{
    return callFetch<TargetModel>(`${Paths.API_ENDPOINT}/competitions/${competitionId}/targets/${targetId}`);
  }

  deleteTarget(targetId: number, competitionId: number) {
    return callFetch(`${Paths.API_ENDPOINT}/competitions/${competitionId}/targets/${targetId}`, {
      method: 'DELETE'
    });
  }










}
