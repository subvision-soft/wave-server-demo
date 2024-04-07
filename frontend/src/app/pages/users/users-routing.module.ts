import { NgModule } from '@angular/core';
import {UsersComponent} from "./components/users/users.component";
import {UserComponent} from "./components/user/user.component";
import {RouterModule, Routes} from "@angular/router";

const routes: Routes = [
  {
    path: '',
    component: UsersComponent,
  },
  {
    path: ':id',
    component: UserComponent
  }
];

@NgModule({
  declarations: [

  ],
  imports: [
    RouterModule.forChild(routes),
  ],

})
export class UsersRoutingModule { }
