import {VacationType} from '../enums/common.enum';

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
      from: Date,
      to: Date,
      type: VacationType;
    }
  ];
}
