import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BasicService } from './basic.service';
import { catchError } from 'rxjs/operators';

import { UserBasicInformation } from '../models/user-basic-information.model';
import { Requests } from '../models/requests.model';

@Injectable({
  providedIn: 'root'
})
export class UsersService extends BasicService {
  private usersUrl = this.baseUrl + '/users?status=';
  private requestsUrl = this.baseUrl + '/users/requests?type=';

  getAuthorizedUsers() {
    return this.getUsersWithStatus('AUTHORIZED');
  }

  getPendingUsers() {
    return this.getUsersWithStatus('PENDING');
  }

  getRejectedUsers() {
    return this.getUsersWithStatus('REJECTED');
  }

  private getUsersWithStatus(status: string) {
    return this.http.get<UserBasicInformation[]>(this.usersUrl + status)
      .pipe(
        catchError(err => this.handleError(err))
      );
  }

  getVacationRequests() {
    return this.getRequestsWithType('VACATION');
  }

  getAuthorizationRequests() {
    return this.getRequestsWithType('AUTHORIZATION');
  }

  private getRequestsWithType(type: string) {
    return this.http.get<Requests>(this.requestsUrl + type)
      .pipe(
        catchError(err => this.handleError(err))
      );
  }

  constructor(protected http: HttpClient) {
    super(http);
  }

}
