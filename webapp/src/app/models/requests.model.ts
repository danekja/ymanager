import {RequestStatus, VacationType} from '../enums/common.enum';
import {Time} from '@angular/common';

export interface VacationRequest {
  id: number;
  firstName: string;
  lastName: string;
  date: Date;
  from: Time;
  to: Time;
  type: VacationType;
  timestamp: Date;
}


export interface AuthorizationRequest {
  id: number;
  firstName: string;
  lastName: string;
  timestamp: Date;
}

/**
 * bude se mazat
 */
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

export interface UserRequest {
  id: number;
  status: RequestStatus;
}
