import { NgModule } from '@angular/core';
import {CommonModule, NgForOf, NgIf} from '@angular/common';
import {CompetitionsComponent} from "./components/competitions/competitions.component";
import {CompetitionComponent} from "./components/competition/competition.component";
import {CompetitionsRoutingModule} from "./competitions-routing.module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgIcon} from "@ng-icons/core";
import { QrCodeModule } from 'ng-qrcode';
import {TargetComponent} from "../../components/target/target.component";
import {TableModule} from "primeng/table";
import {ButtonModule} from "primeng/button";
import {ContextMenuModule} from "primeng/contextmenu";
import {PanelModule} from "primeng/panel";
import {InputTextModule} from "primeng/inputtext";
import {CalendarModule} from "primeng/calendar";
import {ResultPipe} from "../../pipes/result.pipe";
import {ConfirmDialogModule} from "primeng/confirmdialog";
import {ToastModule} from "primeng/toast";
import {DialogModule} from "primeng/dialog";
import {RadioButtonModule} from "primeng/radiobutton";
import {InputSwitchModule} from "primeng/inputswitch";
import {NamePipe} from "../../pipes/name.pipe";



@NgModule({
  declarations: [CompetitionsComponent,CompetitionComponent],

  imports: [
    CommonModule,
    CompetitionsRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    ReactiveFormsModule,
    NgIcon,
    QrCodeModule,
    NgIf,
    NgForOf,
    NgIcon,
    NgIcon,
    TargetComponent,
    TableModule,
    ButtonModule,
    ContextMenuModule,
    PanelModule,
    InputTextModule,
    CalendarModule,
    ResultPipe,
    ConfirmDialogModule,
    ToastModule,
    DialogModule,
    RadioButtonModule,
    InputSwitchModule,
    NamePipe
  ]
})
export class CompetitionsModule { }
