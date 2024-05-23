import {Component} from '@angular/core';
import {CompetitionsService} from "../../../../services/competitions.service";
import {CompetitionModel} from "../../../../models/competition.model";
import {ActivatedRoute} from "@angular/router";
import {CompetitorModel} from "../../../../models/competitor.model";
import {CompetitorsService} from "../../../../services/competitors.service";
import {jamMinus, jamPlus} from '@ng-icons/jam-icons';
import {TargetModel} from "../../../../models/target.model";
import {Paths} from "../../../../statics/Paths";
import {Client} from '@stomp/stompjs';
import {EventEnum} from "../../../../models/event.enum";
import {ResultPipe} from "../../../../pipes/result.pipe";
import {ConfirmationService, MessageService} from "primeng/api";

interface Column {
  field: string;
  header: string;
  customExportHeader?: string;
}

interface ExportColumn {
  title: string;
  dataKey: string;
}
@Component({
  selector: 'app-competition',

  templateUrl: './competition.component.html',
  styleUrl: './competition.component.scss'
})
export class CompetitionComponent {

  lastTarget: TargetModel;

  get targets(): TargetModel[] {
    return this._targets;
  }

  set targets(value: TargetModel[]) {
    this._targets = value;
    this.lastTarget = value[0];
    this.targetsByEvent = [];
    let events = this._targets.reduce((acc:{
      [key: string]: TargetModel[]
    }, target) => {
      if (!acc[target.event]) {
        acc[target.event] = [];
      }
      acc[target.event].push(target);
      return acc;
    }, {});

    Object.entries(events).forEach(([key, value]) => {
      events[key] = value.sort((a, b) =>b.totalScore - a.totalScore);
      this.targetsByEvent.push({
        event: key as EventEnum,
        targets: value
      });
    });
  }

  competition: CompetitionModel;

  competitors: CompetitorModel[];

  private _targets: TargetModel[];


  cols: Column[] = [
    { field: 'competitorId', header: 'Compétiteur' },
    { field: 'totalScore', header: 'Score' },
  ];
  exportColumns: ExportColumn[] = this.cols.map((col) => {
    return {
      title: col.customExportHeader || col.header,
        dataKey
    :
      col.field,
    }
  });


  targetsByEvent: {
    event:EventEnum,
    targets: TargetModel[]
  }[] = [];

  stompClient: Client;

  subscribedCompetitors: CompetitorModel[];

  qrcode: string;
  jamPlus = jamPlus;
  jamMinus = jamMinus;

  constructor(
    private competitionsService: CompetitionsService,
    private route: ActivatedRoute,
    private competitorsService: CompetitorsService,
    private resultPipe: ResultPipe,
    private confirmationService: ConfirmationService, private messageService: MessageService
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

    this.stompClient = new Client({
      brokerURL: Paths.API_ENDPOINT_WS,
    });


    this.stompClient.onConnect= (frame) => {
      console.log('connected', frame);
      this.stompClient.subscribe(`/competitions/${id}/targets/update`, (message) => {
        const data = JSON.parse(message.body);
        this.loadTarget(data);
      });
    }

    this.stompClient.onWebSocketError = (event) => {
      console.error('error', event);
    }

    this.stompClient.onStompError = (frame) => {
      console.error('stomp error', frame);
    }

    this.stompClient.activate();
  }

  loadTarget(id:number) {
    this.competitionsService.getTarget(this.competition.id, id).then((data) => {
      if (this.targets.find((target) => target.id === id)) {
        this.targets = this.targets.map((target) => {
          if (target.id === id) {
            return data;
          }
          return target;
        });
      } else {
        this.targets = [...this.targets, data];
      }
      this.lastTarget = data;
    });
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
    this.confirmationService.confirm({
      message: 'Êtes-vous sûr de vouloir supprimer ce compétiteur ?',
      accept: () => {
        this.competitionsService.removeCompetitor(this.competition.id, competitor.id).then(() => {
          this.loadCompetitors();
          this.messageService.add({severity:'success', summary:'Succès', detail:'Compétiteur supprimé'});
        });
      },
      reject: () => {
        this.messageService.add({severity:'info', summary:'Info', detail:'Suppression annulée'});
      }
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

  deleteTarget(target: any) {

    this.confirmationService.confirm({
      message: 'Êtes-vous sûr de vouloir supprimer cette cible ?',
      accept: () => {
        this.competitionsService.deleteTarget(target.id,target.competitionId).then(() => {
          this.loadTargets();
          this.messageService.add({severity:'success', summary:'Succès', detail:'Cible supprimée'});
        });
      },
      reject: () => {
        this.messageService.add({severity:'info', summary:'Info', detail:'Suppression annulée'});
      }
    });
  }

  protected readonly Paths = Paths;
  showQrCode: boolean = false;
  showLastInput: boolean = false;
}
