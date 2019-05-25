import {VacationType} from '../enums/common.enum';
import {Time} from '@angular/common';

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
