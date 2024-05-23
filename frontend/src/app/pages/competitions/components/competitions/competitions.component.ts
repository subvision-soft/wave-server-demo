import {Component} from '@angular/core';
import {CompetitionsService} from "../../../../services/competitions.service";
import {CompetitionModel} from "../../../../models/competition.model";
import {FormControl, FormGroup} from '@angular/forms';
import {Router} from "@angular/router";
import {jamPlus, jamTrash} from '@ng-icons/jam-icons';
import {ConfirmationService, MenuItem, MessageService} from "primeng/api";

@Component({
  selector: 'app-competitions',

  templateUrl: './competitions.component.html',
  styleUrl: './competitions.component.scss'
})
export class CompetitionsComponent {
  items!: MenuItem[];
  selectedCompetition: CompetitionModel;
  competitions: CompetitionModel[] = [];
  competitionForm: FormGroup = new FormGroup({
    name: new FormControl(''),
    description: new FormControl(''),
    date: new FormControl(''),
  })
  jamPlus = jamPlus;
  jamTrash = jamTrash;


  constructor(private competitionService: CompetitionsService,
              private router: Router,
              private confirmationService: ConfirmationService,
              private messageService: MessageService) {
    this.loadCompetitions();

    this.items = [
      { label: 'View', icon: 'pi pi-eye', command: () => this.openCompetition(this.selectedCompetition.id) },
      { label: 'Delete', icon: 'pi pi-trash', command: () => this.deleteCompetition(this.selectedCompetition.id) }
    ];
  }


  loadCompetitions() {
    this.competitionService.getAll().then((data) => {
        this.competitions = data;
    });
  }

  submit() {
    this.competitionService.create(this.competitionForm.value).then(() => {
      this.loadCompetitions();
      this.competitionForm.reset();
    });
  }

  openCompetition(id: any) {
    this.router.navigate(['/competitions', id]);
  }

  deleteCompetition(id: any) {
    this.confirmationService.confirm({
      message: 'Êtes-vous sûr de vouloir supprimer cette compétition ?',
      accept: () => {
        this.competitionService.delete(id).then(response => {
          this.loadCompetitions();
          this.messageService.add({severity: 'success', summary: 'Success', detail: 'Compétition supprimée'});
        });
      },
      reject: () => {
        this.messageService.add({severity: 'info', summary: 'Info', detail: 'Suppression annulée'});
      }
    });



  }
}
