import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BasicService } from './basic.service';
import {catchError, map} from 'rxjs/operators';

import {UserBasicInformation} from '../models/user-basic-information.model';
import { Requests } from '../models/requests.model';
import {ProfileStatus, RequestTypes} from '../enums/common.enum';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsersService extends BasicService {
  private usersUrl = this.baseUrl + '/users?status=';
  private requestsUrl = this.baseUrl + '/users/requests?type=';

  getAuthorizedUsers() {
    return this.getUsersWithStatus(ProfileStatus.AUTHORIZED);
  }

  getPendingUsers() {
    return this.getUsersWithStatus(ProfileStatus.PENDING);
  }

  getRejectedUsers() {
    return this.getUsersWithStatus(ProfileStatus.REJECTED);
  }

  private getUsersWithStatus(status: string): Observable<UserBasicInformation[]> {
    console.log(this.usersUrl + status);
    return this.http.get<UserBasicInformation[]>(this.usersUrl + status)
      .pipe(
        catchError(err => this.handleError(err))
      );
  }

  getVacationRequests() {
    return this.getRequestsWithType(RequestTypes.VACATION);
  }

  getAuthorizationRequests() {
    return this.getRequestsWithType(RequestTypes.AUTHORIZATION);
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