import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'time',
  standalone: true
})
export class TimePipe implements PipeTransform {
  private _time: number;

  transform(value: number): unknown {
    this._time = value;
    return `${this.minutes.toString().padStart(2, '0')}:${this.seconds.toString().padStart(2, '0')}.${this.centiseconds.toString().padStart(2, '0')}`;
  }


  get minutes(): number {
    return Math.floor(this._time / (60 * 1000)) % 60;
  }

  get seconds(): number {
    return Math.floor(this._time / 1000) % 60;
  }

  get centiseconds(): number {
    return Math.floor(this._time / 10) % 100;
  }

}
