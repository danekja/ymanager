import {RequestStatus} from '../enums/common.enum';

export interface UserRequest {
  id: number;
  status: RequestStatus;
}
