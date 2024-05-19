import { Component } from '@angular/core';
import {Paths} from "./statics/Paths";
import {jamHome, jamUsers, jamFlag, jamMenu} from "@ng-icons/jam-icons";
import {MenuItem} from "primeng/api";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'frontend';
  menuItems: MenuItem[] = [
    {
      label: 'Home',
      icon: 'pi pi-home',
      routerLink: '/' + Paths.HOME,
      // icon:jamHome
    },
    {
      label: 'Users',
      icon: 'pi pi-users',
      routerLink: '/' + Paths.USERS,
      // icon:jamUsers
    },
    {
      label: 'Competitions',
      icon: 'pi pi-flag',
      routerLink: '/competitions',
      // icon:jamFlag
    }
  ];

  protected readonly jamMenu = jamMenu;
  collapsed: boolean = true;
}
