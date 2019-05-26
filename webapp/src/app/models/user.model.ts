import {Time} from '@angular/common';
import {RequestStatus, UserType, VacationType} from '../enums/common.enum';

export interface UserBasicInformation {
  id: number;
  firstName: string;
  lastName: string;
  photo: string;
  calendar: [
    {
      id: number,
      date: Date,
      from: Time,
      to: Time,
      type: VacationType;
    }
    ];
}

export interface UserProfile {
  id: number;
  firstName: string;
  lastName: string;
  photo: string;
  vacationCount: number;
  sickdayCount: number;
  status: RequestStatus;
  role: UserType;
  notification: Date;
}
