import {UserType} from '../enums/common.enum';

export interface Settings {
  sickDayCount: number;
  notification: string;
}

export interface UserSettings {
  id: number;
  vacationCount: number;
  sickDayCount: number;
  role: UserType;
}

export interface NotificationSettings {
  notification: string;
}
