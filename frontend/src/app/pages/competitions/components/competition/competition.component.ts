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

  qrcode: string;

  constructor(private competitionService: CompetitionsService, private route: ActivatedRoute) {
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) {
      return;
    }
    competitionService.get(parseInt(id) ).then((res) => {
      console.log(res);
      res.json().then((data) => {
        this.competition = data;
        this.generateQrCode();

      });
    });

  }

  generateQrCode() {
    fetch(`http://localhost:8080/api/adress`).then((res) => {
      res.text().then((data) => {
        this.qrcode = `http://${data}/competitions/${this.competition.id}`;
      });
    });
  }

}
