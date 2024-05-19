import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {TargetsService} from "./services/targets.service";
import { CardModule } from 'primeng/card';
import { MenuModule } from 'primeng/menu';
import { ButtonModule } from 'primeng/button';
import {provideAnimations} from "@angular/platform-browser/animations";
import {PanelModule} from "primeng/panel";
@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CardModule,
    MenuModule,
    PanelModule,
  ],
  providers: [
    TargetsService,
    provideAnimations()
  ],
  bootstrap: [AppComponent],
})
export class AppModule { }
