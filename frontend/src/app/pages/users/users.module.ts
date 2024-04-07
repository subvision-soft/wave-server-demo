import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {UserComponent} from "./components/user/user.component";
import {UsersComponent} from "./components/users/users.component";
import {UsersRoutingModule} from "./users-routing.module";



@NgModule({
  declarations: [UserComponent,UsersComponent],
  imports: [
    CommonModule,
    UsersRoutingModule
  ]
})
export class UsersModule { }
