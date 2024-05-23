import { Pipe, PipeTransform } from '@angular/core';
import {CompetitorsService} from "../services/competitors.service";

@Pipe({
  name: 'name',
  standalone: true
})
export class NamePipe implements PipeTransform {

  async transform(value: number): Promise<string> {
    let competitorModel = await this.competitorService.get(value);
    return `${competitorModel.firstName} ${competitorModel.lastName} (${competitorModel.id})`;
  }

  constructor(private competitorService: CompetitorsService) {
  }

}
