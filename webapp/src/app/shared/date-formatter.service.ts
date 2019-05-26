import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DateFormatterService {
  constructor() { }

  /**
   * Formats date to a following format: yyyy/mm/dd hh:mm:ss
   * @param date Date which is formatted
   */
  formatDate(date: Date): string {
    let result = '';

    result += this.formatToDoubleDigits(date.getFullYear()) + '/';
    result += this.formatToDoubleDigits(date.getMonth() + 1) + '/';
    result += this.formatToDoubleDigits(date.getDate()) + ' ';

    result += this.formatToDoubleDigits(date.getHours()) + ':';
    result += this.formatToDoubleDigits(date.getMinutes()) + ':';
    result += this.formatToDoubleDigits(date.getSeconds());

    return result;
  }

  /**
   * Formats passed number to double digits format (e.g. - 3 -> '03')
   * @param number Number which is formatted
   */
  private formatToDoubleDigits(number: number): string {
    if (number < 10) {
      return '0' + number;
    } else {
      return '' + number;
    }
  }
}
