import {RequestStatus, TimeUnit, UserType} from '../enums/common.enum';

export interface PostRequest {
  id: number;
  status: RequestStatus;
}

export interface PostSettings {
  role: UserType;
  vacation: {
    value: number;
    unit: TimeUnit;
  };
  sickday: {
    value: number;
    unit: TimeUnit;
  };
}
