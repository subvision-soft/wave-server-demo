import {Component, ElementRef, ViewChild} from '@angular/core';
import {CompetitionsService} from "../../services/competitions.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Paths} from "../../statics/Paths";
import {CompetitionModel} from "../../models/competition.model";
import {TargetModel} from "../../models/target.model";
import {AsyncPipe, KeyValuePipe, NgForOf, NgIf} from "@angular/common";
import {ButtonModule} from "primeng/button";
import {NamePipe} from "../../pipes/name.pipe";
import {SharedModule} from "primeng/api";
import {TableModule} from "primeng/table";
import {PanelModule} from "primeng/panel";
import html2canvas from 'html2canvas';
import jsPDF from "jspdf";

class Column {
  field: string;
  header: string;
  customExportHeader?: string;
}

@Component({
  selector: 'app-results',
  standalone: true,
  imports: [
    NgForOf,
    KeyValuePipe,
    NgIf,
    AsyncPipe,
    ButtonModule,
    NamePipe,
    SharedModule,
    TableModule,
    PanelModule
  ],
  templateUrl: './results.component.html',
  styleUrl: './results.component.scss'
})
export class ResultsComponent {

  @ViewChild('pdfrender') pdfrender: ElementRef;

  get targets(): TargetModel[] {
    return this._targets;
  }

  set targets(value: TargetModel[]) {
    this._targets = value;
    this.results = new Map<string, Map<string, TargetModel[]>>();
    value.forEach((target) => {
      if (this.results.has(this.getLibelleEpreuve(target))) {
        if (this.results.get(this.getLibelleEpreuve(target))?.has(this.getLibelleCategorie(target))) {
          this.results.get(this.getLibelleEpreuve(target))?.set(this.getLibelleCategorie(target),

            [...(this.results.get(this.getLibelleEpreuve(target))?.get(this.getLibelleCategorie(target)) || []), target].sort((a, b) => b.totalScore - a.totalScore)
          );
        } else {
          this.results.get(this.getLibelleEpreuve(target))?.set(this.getLibelleCategorie(target), [target]);
        }
      } else {
        this.results.set(this.getLibelleEpreuve(target), new Map<string, TargetModel[]>());
        this.results.get(this.getLibelleEpreuve(target))?.set(this.getLibelleCategorie(target), [target]);
      }
    });
  }

  cols: Column[] = [
    {field: 'competitorId', header: 'Compétiteur'},
    {field: 'totalScore', header: 'Score'},
  ];

  getLibelleEpreuve(target: TargetModel) {

    return [target.event, target.stage].filter((e) => !!e).join(' - ');
  }

  getLibelleCategorie(target: TargetModel) {
    return [target.competitor.category, target.competitor.sex?.charAt(0)].filter((e) => e !== undefined).join(' - ');
  }

  results: Map<string, Map<string, TargetModel[]>>


  private competition: CompetitionModel;
  private _targets: TargetModel[];

  constructor(
    private competitionsService: CompetitionsService,
    private route: ActivatedRoute,
    private router: Router,
    private elementRef: ElementRef
  ) {
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) {
      this.router.navigate([Paths.COMPETITIONS]);
      return;
    }
    competitionsService.get(parseInt(id)).then((data) => {
      this.competition = data;
      this.loadTargets();
    });
  }

  loadTargets() {
    this.competitionsService.getTargets(this.competition.id).then((data) => {
      this.targets = [...data];
    });
  }

  saveAsPDF() {
    const data = this.pdfrender.nativeElement;
    html2canvas(data).then(canvas => {
      const imgData = canvas.toDataURL('image/png');
      const pdf = new jsPDF({
        orientation: 'p',
      });
      pdf.html(data, {
        html2canvas: {
          scale: 0.21,
        },

        callback: function (pdf) {
          pdf.save('resultats.pdf');
        },
        margin: 10,

      });

      // //add pages if content is too long, image must be split in multiple pages
      // const imgWidth = 297;
      // const pageHeight = 210;
      // const imgHeight = canvas.height * imgWidth / canvas.width;
      // let heightLeft = imgHeight;
      // let position = 0;
      // pdf.addImage(imgData, 'PNG', 0, position, imgWidth, imgHeight);
      // heightLeft -= pageHeight;
      // while (heightLeft >= 0) {
      //   position = heightLeft - imgHeight;
      //   pdf.addPage();
      //   pdf.addImage(imgData, 'PNG', 0, position, imgWidth, imgHeight);
      //   heightLeft -= pageHeight;
      // }
      // pdf.save('resultats.pdf');

    });
  }
}
