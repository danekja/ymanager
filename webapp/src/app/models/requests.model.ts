import {Time} from '@angular/common';
import {RequestStatus, VacationType} from '../enums/common.enum';

export interface Requests {
  vacation: [
    {
      id: number;
      user: {
        name: {
          first: string;
          last: string;
        };
      };
      date: Date;
      from: Time;
      to: Time;
      type: VacationType;
      status: RequestStatus;
    }
  ];
  authorization: [
    {
      id: number;
      user: {
        name: {
          first: string;
          last: string;
        };
      };
      date: Date;
    }
  ];
}
