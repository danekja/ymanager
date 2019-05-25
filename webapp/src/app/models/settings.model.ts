import {UserType} from '../enums/common.enum';

export interface Settings {
  sickdayCount: number;
  notification: string;
}

export interface PostUserSettings {
  id: number;
  vacationCount: number;
  sickdayCount: number;
  role: UserType;
}

