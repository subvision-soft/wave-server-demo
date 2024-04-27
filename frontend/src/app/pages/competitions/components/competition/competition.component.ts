import {Component} from '@angular/core';
import {CompetitionsService} from "../../../../services/competitions.service";
import {CompetitionModel} from "../../../../models/competition.model";
import {ActivatedRoute} from "@angular/router";
import {CompetitorModel} from "../../../../models/competitor.model";
import {CompetitorsService} from "../../../../services/competitors.service";
import {jamMinus, jamPlus } from '@ng-icons/jam-icons';

@Component({
  selector: 'app-competition',

  templateUrl: './competition.component.html',
  styleUrl: './competition.component.scss'
})
export class CompetitionComponent {

  competition: CompetitionModel;

  competitors: CompetitorModel[];

  subscribedCompetitors: CompetitorModel[];

  qrcode: string;
  jamPlus=jamPlus;
  jamMinus=jamMinus;

  constructor(
    private competitionsService: CompetitionsService,
    private route: ActivatedRoute,
    private competitorsService: CompetitorsService
    ) {
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) {
      return;
    }
    competitionsService.get(parseInt(id)).then((res) => {
      console.log(res);
      res.json().then((data) => {
        this.competition = data;
        this.generateQrCode();
        this.loadCompetitors();
      });
    });

  }


  addCompetitor(competitor: CompetitorModel) {
    this.competitionsService.addCompetitor(this.competition.id, competitor.id).then(() => {
      this.loadCompetitors();
    });
  }

  removeCompetitor(competitor: CompetitorModel) {
    this.competitionsService.removeCompetitor(this.competition.id, competitor.id).then(() => {
      this.loadCompetitors();
    });
  }

  loadCompetitors() {
    this.competitorsService.getAll().then((res) => {
      res.json().then((data) => {
        this.competitors = data;
      });
    });
    this.competitionsService.getCompetitors(this.competition.id).then((res) => {
      res.json().then((data) => {
        this.subscribedCompetitors = data;
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
