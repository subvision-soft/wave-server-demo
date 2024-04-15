import { NgModule } from '@angular/core';
import {CommonModule, NgForOf} from '@angular/common';
import {UserComponent} from "./components/user/user.component";
import {UsersComponent} from "./components/users/users.component";
import {UsersRoutingModule} from "./users-routing.module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgIcon} from "@ng-icons/core";
import {ComponentsModule} from "../../components/components.module";



@NgModule({
  declarations: [UserComponent,UsersComponent],
    imports: [
        CommonModule,
        UsersRoutingModule,
        NgForOf,
        FormsModule,
        ReactiveFormsModule,
        ReactiveFormsModule,
        ReactiveFormsModule,
        ReactiveFormsModule,
        NgIcon,
        NgIcon,
        NgIcon,
        NgIcon,
        NgIcon,
        NgIcon,
        ComponentsModule
    ]
})
export class UsersModule { }
