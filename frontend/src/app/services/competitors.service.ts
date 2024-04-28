import {Injectable} from "@angular/core";
import {CompetitorModel} from "../models/competitor.model";
import {Paths} from "../statics/Paths";

@Injectable({
  providedIn: 'root'

})
export class CompetitorsService {

  constructor() {
  }

  getAll():Promise<Response> {
    return fetch(`${Paths.API_ENDPOINT}/competitors`)
  }

  create(data:CompetitorModel) {
    return fetch(`${Paths.API_ENDPOINT}/competitors`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    })
  }

  get(id:number) {
    return fetch(`${Paths.API_ENDPOINT}/competitors/${id}`)
  }

  delete(id:number) {
    return fetch(`${Paths.API_ENDPOINT}/competitors/${id}`, {
      method: 'DELETE'
    })
  }

}
