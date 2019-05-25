import {RequestStatus, TimeUnit, UserType} from '../enums/common.enum';

export interface UserRequest {
  id: number;
  status: RequestStatus;
}
