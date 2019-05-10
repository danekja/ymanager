import {VacationType} from '../enums/common.enum';

export class DayInfo {
  date: Date;
  type: VacationType;
}

export class User {
  id: number;
  name: string;
  imageLink: string;
  dates: DayInfo[];
}

