import { Component } from '@angular/core';
import {CompetitorModel} from "../../../../models/competitor.model";
import {CompetitorsService} from "../../../../services/competitors.service";
import { FormGroup, FormControl } from '@angular/forms';

import {jamTrash,jamPlus} from '@ng-icons/jam-icons';
import {Router} from "@angular/router";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrl: './users.component.scss'
})
export class UsersComponent {

  jamTrash = jamTrash;

  competitors : CompetitorModel[] = [];
  competitorForm: FormGroup = new FormGroup({
    id: new FormControl('',{
      nonNullable: true
    }),
    firstName: new FormControl('',{
      nonNullable: true
    }),
    lastName: new FormControl('',{
      nonNullable: true
    }),
    category: new FormControl('',{
      nonNullable: true
    }),
    sex: new FormControl('',{
      nonNullable: true
    }),
  })
  jamPlus = jamPlus;

  constructor(private competitorsService: CompetitorsService,private router: Router,private messageService: MessageService) {
    this.loadCompetitors();
  }

  loadCompetitors() {
    this.competitorsService.getAll().then(response => response.json()).then(data => {
      this.competitors = data;
    });

  }

  onSubmit() {
    if (this.competitorForm.invalid) {
      this.messageService.add({severity:'error', summary: 'Error', detail: 'Veuillez remplir tous les champs obligatoires'});

    } else {
      this.competitorsService.create(this.competitorForm.value).then(response => {
        this.loadCompetitors();
        this.competitorForm.reset();
      });
    }
  }

  deleteCompetitor(id: any) {
    this.competitorsService.delete(id).then(response => {
      this.loadCompetitors();
    });
  }
  openCompetitor(id: any) {
    this.router.navigate(['/users', id]);
  }

}
