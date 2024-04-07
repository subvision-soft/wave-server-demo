import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule} from "@angular/router";
import {HomeComponent} from "./components/home/home.component";


const routes = [
  {
    path: '',
    component: HomeComponent,
  }
  ];

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forChild(routes)]

})
export class HomeRoutingModule { }
