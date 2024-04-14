import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {CompetitionsComponent} from "./components/competitions/competitions.component";
import {CompetitionComponent} from "./components/competition/competition.component";
import {CompetitionsRoutingModule} from "./competitions-routing.module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";



@NgModule({
  declarations: [CompetitionsComponent,CompetitionComponent],
  imports: [
    CommonModule,
    CompetitionsRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    ReactiveFormsModule
  ]
})
export class CompetitionsModule { }
