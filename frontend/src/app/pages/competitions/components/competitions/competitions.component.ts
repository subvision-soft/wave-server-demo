import { Component } from '@angular/core';
import {CompetitionsService} from "../../../../services/competitions.service";
import {CompetitionModel} from "../../../../models/competition.model";
import { FormGroup, FormControl } from '@angular/forms';
import {Router} from "@angular/router";
@Component({
  selector: 'app-competitions',

  templateUrl: './competitions.component.html',
  styleUrl: './competitions.component.scss'
})
export class CompetitionsComponent {

  competitions:CompetitionModel[] = [];
  competitionForm: FormGroup = new FormGroup({
    name: new FormControl(''),
    description: new FormControl(''),
    date: new FormControl(''),
  })



constructor(private competitionService: CompetitionsService,private router: Router) {
  competitionService.getAll().then((res) => {
    console.log(res);
    res.json().then((data) => {
      this.competitions = data;
    });
  });
}

  submit() {
    this.competitionService.create(this.competitionForm.value).then((res) => {
      console.log(res);
    });




  }

  openCompetition(id: any) {
    this.router.navigate(['/competitions', id]);
  }
}
