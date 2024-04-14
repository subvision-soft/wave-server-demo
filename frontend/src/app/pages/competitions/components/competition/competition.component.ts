import { Component } from '@angular/core';
import {CompetitionsService} from "../../../../services/competitions.service";
import {CompetitionModel} from "../../../../models/competition.model";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-competition',

  templateUrl: './competition.component.html',
  styleUrl: './competition.component.scss'
})
export class CompetitionComponent {

  competition:CompetitionModel;

  constructor(private competitionService: CompetitionsService, private route: ActivatedRoute) {
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) {
      return;
    }
    competitionService.get(parseInt(id) ).then((res) => {
      console.log(res);
      res.json().then((data) => {
        this.competition = data;
      });
    });

  }

}
