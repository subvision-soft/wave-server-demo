import {Component} from '@angular/core';
import {CompetitionsService} from "../../../../services/competitions.service";
import {CompetitionModel} from "../../../../models/competition.model";
import {ActivatedRoute} from "@angular/router";
import {CompetitorModel} from "../../../../models/competitor.model";
import {CompetitorsService} from "../../../../services/competitors.service";
import {jamMinus, jamPlus } from '@ng-icons/jam-icons';
import {TargetModel} from "../../../../models/target.model";
import {Paths} from "../../../../statics/Paths";

@Component({
  selector: 'app-competition',

  templateUrl: './competition.component.html',
  styleUrl: './competition.component.scss'
})
export class CompetitionComponent {

  competition: CompetitionModel;

  competitors: CompetitorModel[];

  targets: TargetModel[];

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
    competitionsService.get(parseInt(id)).then((data) => {
        this.competition = data;
        this.generateQrCode();
        this.loadCompetitors();
        this.loadTargets();
    });

    let ws = new WebSocket(`${Paths.API_ENDPOINT_WS}/competitions/${id}/update`);
    ws.onmessage = (event) => {
      this.loadTargets();
    }

  }


  loadTargets() {
    this.competitionsService.getTargets(this.competition.id).then((data) => {
      this.targets = [...data];
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
    this.competitionsService.getCompetitors(this.competition.id).then((data) => {
        this.subscribedCompetitors = data;
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
