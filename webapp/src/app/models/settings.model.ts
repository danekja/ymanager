import {UserType} from '../enums/common.enum';

export interface Settings {
  sickdayCount: number;
  notification: string;
}

export interface UserSettings {
  id: number;
  vacationCount: number;
  sickdayCount: number;
  role: UserType;
}

