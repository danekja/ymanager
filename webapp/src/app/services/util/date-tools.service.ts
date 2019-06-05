import {Injectable} from '@angular/core';

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

  /**
   * Creates {date: Date, time: string} object from string representation of datetime in format 'yyyy/mm/dd hh:mm:ss'
   * @param datetime string representation of datetime in format 'yyyy/mm/dd hh:mm:ss'
   */
  toDateAndTime(datetime: string) {
    const parsedDatetime = datetime.split(' ');
    const parsedDate = parsedDatetime[0].split('/');
    const parsedTime = parsedDatetime[1].split(':');

    const date = new Date(Number(parsedDate[0]), Number(parsedDate[1]) - 1, Number(parsedDate[2]));
    const time = parsedTime[0] + ':' + parsedTime[1];

    return {
      date,
      time
    };
  }
}
