import {Component} from '@angular/core';
import {CompetitionsService} from "../../../../services/competitions.service";
import {CompetitionModel} from "../../../../models/competition.model";
import {FormControl, FormGroup} from '@angular/forms';
import {Router} from "@angular/router";
import {jamPlus, jamTrash} from '@ng-icons/jam-icons';

@Component({
  selector: 'app-competitions',

  templateUrl: './competitions.component.html',
  styleUrl: './competitions.component.scss'
})
export class CompetitionsComponent {

  competitions: CompetitionModel[] = [];
  competitionForm: FormGroup = new FormGroup({
    name: new FormControl(''),
    description: new FormControl(''),
    date: new FormControl(''),
  })
  jamPlus = jamPlus;
  jamTrash = jamTrash;


  constructor(private competitionService: CompetitionsService, private router: Router) {
    this.loadCompetitions();
  }


  loadCompetitions() {
    this.competitionService.getAll().then((res) => {
      res.json().then((data) => {
        this.competitions = data;
      });
    });
  }

  submit() {
    this.competitionService.create(this.competitionForm.value).then((res) => {
      this.loadCompetitions();
      this.competitionForm.reset();
    });
  }

  openCompetition(id: any) {
    this.router.navigate(['/competitions', id]);
  }

  deleteCompetition(id: any, $event: Event) {
    $event.stopPropagation();
    this.competitionService.delete(id).then(response => {
      this.loadCompetitions();
    });
  }
}
