import {TimeUnit} from '../enums/common.enum';

export interface UserProfile {
  id: number;
  name: {
    first: string;
    last: string;
  };
  photo: string;
  settings: {
    notification: Date;
  };
  vacation: {
    value: number;
    unit: TimeUnit;
  };
  sickDay: {
    value: number;
    unit: TimeUnit;
  };
}

