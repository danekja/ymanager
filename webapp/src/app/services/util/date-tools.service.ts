import {Injectable} from '@angular/core';
import {Time} from "@angular/common";

@Injectable({
  providedIn: 'root'
})
export class DateToolsService {
  constructor() { }

  /**
   * Returns start of passed month
   * @param date date
   */
  toStartOfMonth(date: Date): Date {
    return new Date(
      date.getFullYear(),
      date.getMonth(),
      1,
      0,
      0,
      0,
      0
    );
  }

  /**
   * Returns end of passed month
   * @param date date
   */
  toEndOfMonth(date: Date): Date {
    return new Date(
      date.getFullYear(),
      date.getMonth() + 1,
      0,
      23,
      59,
      59,
      999
    );
  }

  /**
   * Creates Date from date and time
   *
   * @param date date in format YYYY/mm/dd
   * @param time time
   */
  toDate(date: string, time: string): Date {
    const splittedDate = date.split('/');

    const result = new Date(
      Number(splittedDate[0]),
      Number(splittedDate[1]) - 1,
      Number(splittedDate[2])
    );

    if (time) {
      const splittedTime = time.split(':');
      result.setHours(Number(splittedTime[0]));
      result.setMinutes(Number(splittedTime[1]));
    }

    return result;
  }
}
