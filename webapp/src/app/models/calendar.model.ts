import {Time} from '@angular/common';
import {RequestStatus, VacationType} from '../enums/common.enum';

export interface Calendar {
  id: number;
  date: string;
  from: Time;
  to: Time;
  type: VacationType;
  status: RequestStatus;
}


export interface PostCalendar {
  date: string;
  from: string;
  to: string;
  type: VacationType;
}

export interface CalendarEdit {
  id: number;
  date: string;
  from: string;
  to: string;
}
