import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {CompetitionsComponent} from "./components/competitions/competitions.component";
import {CompetitionComponent} from "./components/competition/competition.component";
import {CompetitionsRoutingModule} from "./competitions-routing.module";



@NgModule({
  declarations: [CompetitionsComponent,CompetitionComponent],
  imports: [
    CommonModule,
    CompetitionsRoutingModule
  ]
})
export class CompetitionsModule { }
