import {Time} from '@angular/common';
import {RequestStatus, VacationType} from '../enums/common.enum';

export interface Calendar {
  date: Date;
  from: Time;
  to: Time;
  type: VacationType;
  status: RequestStatus;
}
