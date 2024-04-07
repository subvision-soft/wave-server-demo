import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule} from "@angular/router";
import {CompetitionsComponent} from "./components/competitions/competitions.component";
import {CompetitionComponent} from "./components/competition/competition.component";

const routes = [
  {
    path: '',
    component: CompetitionsComponent,
  }
  ,{
    path: ':id',
    component: CompetitionComponent,
  }
  ];


@NgModule({
  declarations: [],
  imports: [
    RouterModule.forChild(routes)
  ]
})
export class CompetitionsRoutingModule { }
