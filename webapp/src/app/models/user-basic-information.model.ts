import {VacationType} from '../enums/common.enum';
import {Time} from '@angular/common';

export interface UserBasicInformation {
  id: number;
  name: {
    first: string;
    last: string;
  };
  photo: string;
  calendar: [
    {
      date: Date,
      from: Time,
      to: Time,
      type: VacationType;
    }
  ];
}
