import {RequestStatus, UserType} from '../enums/common.enum';

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

