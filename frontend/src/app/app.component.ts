import { Component } from '@angular/core';
import {MenuItemModel} from "ngx-wave-ui";
import {Paths} from "./statics/Paths";
import {jamHome, jamUsers, jamFlag, jamMenu} from "@ng-icons/jam-icons";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'frontend';
  menuItems: MenuItemModel[] = [
    {
      title: 'Home',
      route: '/' + Paths.HOME,
      icon:jamHome
    },
    {
      title: 'Users',
      route: '/' + Paths.USERS,
      icon:jamUsers
    },
    {
      title: 'Competitions',
      route: '/competitions',
      icon:jamFlag
    }
  ];

  protected readonly jamMenu = jamMenu;
  collapsed: boolean = true;
}
