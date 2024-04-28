import {Injectable} from "@angular/core";
import {Paths} from "../statics/Paths";
import {callFetch} from "../statics/Fetch";

@Injectable()
export class TargetsService {
  constructor(

  ) {
  }


  getTargets(competitionId: number) {
    return fetch(`${Paths.API_ENDPOINT}/competitions/${competitionId}/targets`);
  }

  delete(targetId: number) {
    return callFetch(`${Paths.API_ENDPOINT}/targets/${targetId}`, {
      method: 'DELETE'
    });
  }
}
