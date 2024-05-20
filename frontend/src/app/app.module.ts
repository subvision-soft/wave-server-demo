import {LOCALE_ID, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {TargetsService} from "./services/targets.service";
import { CardModule } from 'primeng/card';
import { MenuModule } from 'primeng/menu';
import { ButtonModule } from 'primeng/button';
import {provideAnimations} from "@angular/platform-browser/animations";
import {PanelModule} from "primeng/panel";
import {registerLocaleData} from "@angular/common";
import localeFr from '@angular/common/locales/fr';
import {TranslateModule} from "@ngx-translate/core";
import {ResultPipe} from "./pipes/result.pipe";
import {ScrollPanelModule} from "primeng/scrollpanel";
registerLocaleData(localeFr, 'fr');
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
    TranslateModule.forRoot(),
    ScrollPanelModule
  ],
  providers: [
    TargetsService,
    ResultPipe,
    provideAnimations(),
    {provide: LOCALE_ID, useValue: 'fr' }
  ],
  bootstrap: [AppComponent],
})
export class AppModule { }
