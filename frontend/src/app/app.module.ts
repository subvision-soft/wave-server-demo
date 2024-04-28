import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {NgxWaveUiModule} from "ngx-wave-ui";
import {TargetsService} from "./services/targets.service";
@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgxWaveUiModule,

  ],
  providers: [
    TargetsService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
