import {Component, Input} from '@angular/core';
import {CompetitorModel} from "../../models/competitor.model";
import {ActionModel} from "../../models/action.model";
import {Router} from "@angular/router";

@Component({
  selector: 'app-competitor-table',
  templateUrl: './competitor-table.component.html',
  styleUrl: './competitor-table.component.scss'
})
export class CompetitorTableComponent {
@Input() competitors:CompetitorModel[] = [];
@Input() actions:ActionModel[] = [];

  constructor(private router: Router) {
  }
  callMethod(competitor:CompetitorModel, action:ActionModel, $event: MouseEvent) {
    $event.stopPropagation();
    action.method(competitor);
  }

  openCompetitor(id: any) {
    this.router.navigate(['/users', id]);
  }
}
