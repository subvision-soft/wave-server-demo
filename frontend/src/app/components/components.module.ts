import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {CompetitorTableComponent} from "./competitor-table/competitor-table.component";
import {NgIcon} from "@ng-icons/core";



@NgModule({
  declarations: [CompetitorTableComponent],
  exports: [
    CompetitorTableComponent
  ],
  imports: [
    CommonModule,
    NgIcon
  ]
})
export class ComponentsModule { }
