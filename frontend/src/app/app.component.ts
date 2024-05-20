import {Component, OnInit} from '@angular/core';
import {Paths} from "./statics/Paths";
import {jamHome, jamUsers, jamFlag, jamMenu} from "@ng-icons/jam-icons";
import {MenuItem, PrimeNGConfig} from "primeng/api";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
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

  constructor(private config: PrimeNGConfig, private translateService: TranslateService) {}

  ngOnInit() {
    let langs = ['fr'];
    this.translateService.addLangs(langs);
    this.translateService.setDefaultLang('fr');
    const browserLang = this.translateService.getBrowserLang() || 'fr';
    this.translateService.use(langs.includes(browserLang) ? browserLang : 'fr');
  }

}
