import {Injectable} from "@angular/core";
import {CompetitorModel} from "../models/competitor.model";

@Injectable({
  providedIn: 'root'

})
export class CompetitorsService {

  constructor() {

  }

  endpoint = 'http://localhost:8080/api';

  getAll():Promise<Response> {
    return fetch(`${this.endpoint}/competitors`)
  }

  create(data:CompetitorModel) {
    return fetch(`${this.endpoint}/competitors`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    })
  }

  get(id:number) {
    return fetch(`${this.endpoint}/competitors/${id}`)
  }

  delete(id:number) {
    return fetch(`${this.endpoint}/competitors/${id}`, {
      method: 'DELETE'
    })
  }

}
