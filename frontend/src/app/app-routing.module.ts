import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {Paths} from "./statics/Paths";

const routes: Routes = [
  {
    path: Paths.USERS,
    loadChildren: () => import('./pages/users/users.module').then(m => m.UsersModule)
  },
{
    path: Paths.HOME,
    loadChildren: () => import('./pages/home/home.module').then(m => m.HomeModule)
  },
  {
    path: Paths.COMPETITIONS,
    loadChildren: () => import('./pages/competitions/competitions.module').then(m => m.CompetitionsModule)
  },
  {
    path: '',
    redirectTo: Paths.HOME,
    pathMatch: 'full'
  },
  {
    path: '**',
    redirectTo: Paths.HOME
  }


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
