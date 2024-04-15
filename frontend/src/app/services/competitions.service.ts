import {Injectable} from "@angular/core";
import {CompetitionModel} from "../models/competition.model";

@Injectable({
  providedIn: 'root'

})
export class CompetitionsService {
  constructor() {

  }

  endpoint = 'http://localhost:8080/api';

  getAll():Promise<Response> {
    return fetch(`${this.endpoint}/competitions`)
  }

  create(data:CompetitionModel) {
    return fetch(`${this.endpoint}/competitions`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    })
  }

  delete(id:number) {
    return fetch(`${this.endpoint}/competitions/${id}`, {
      method: 'DELETE'
    })
  }

  get(id:number) {
    return fetch(`${this.endpoint}/competitions/${id}`)
  }

  getCompetitors(id:number) {
    return fetch(`${this.endpoint}/competitions/${id}/competitors`)
  }

  addCompetitor(competitionId:number,competitorId:number) {
    return fetch(`${this.endpoint}/competitions/${competitionId}/competitors/${competitorId}`, {
      method: 'POST'
    })
  }

  removeCompetitor(competitionId:number,competitorId:number) {
    return fetch(`${this.endpoint}/competitions/${competitionId}/competitors/${competitorId}`, {
      method: 'DELETE'
    })
  }
}
