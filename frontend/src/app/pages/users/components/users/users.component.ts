import { Component } from '@angular/core';
import {CompetitorModel} from "../../../../models/competitor.model";
import {CompetitorsService} from "../../../../services/competitors.service";
import { FormGroup, FormControl } from '@angular/forms';

import {jamTrash,jamPlus} from '@ng-icons/jam-icons';
import {Router} from "@angular/router";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrl: './users.component.scss'
})
export class UsersComponent {

  jamTrash = jamTrash;

  competitors : CompetitorModel[] = [];
  competitorForm: FormGroup = new FormGroup({
    id: new FormControl(''),
    firstName: new FormControl(''),
    lastName: new FormControl(''),
    category: new FormControl(''),
  })
  jamPlus = jamPlus;

  constructor(private competitorsService: CompetitorsService,private router: Router) {
    this.loadCompetitors();
  }

  loadCompetitors() {
    this.competitorsService.getAll().then(response => response.json()).then(data => {
      this.competitors = data;
    });

  }

  onSubmit() {
    this.competitorsService.create(this.competitorForm.value).then(response => {
      this.loadCompetitors();
      this.competitorForm.reset();
    });
  }

  deleteCompetitor(id: any,$event: Event) {
    $event.stopPropagation();
    this.competitorsService.delete(id).then(response => {
      this.loadCompetitors();
    });
  }


}
