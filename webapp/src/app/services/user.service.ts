import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { UserProfile } from '../models/user-profile.model';
import { Calendar } from '../models/calendar.model';
import { BasicService } from './basic.service';
import { catchError } from 'rxjs/operators';
import {RequestTypes, TimeUnit} from '../enums/common.enum';
import {PostRequest, PostSettings} from '../models/post-requests.model';

@Injectable({
  providedIn: 'root'
})
export class UserService extends BasicService { // dost podobny k usersService, mozna zmenit v rest api
  private _calendarUrl = this.baseUrl + '/user/calendar';
  private _postRequestUrl = this.baseUrl + '/user/requests?type=';
  private _userUrl = this.baseUrl + '/user/';

  getEmployeeProfile(id: number) { // najit jinej zpusob formatovani stringu, prasarna
    return this.http.get<UserProfile>(this._userUrl + id + '/profile');
  }

  getMonthlyCalendar(value: number) {
    return this.getCalendar(TimeUnit.MONTHLY, value);
  }

  getWeeklyCalendar(value: number) {
    return this.getCalendar(TimeUnit.WEEKLY, value);
  }

  private getCalendar(viewType: string, value: number) {
    return this.http.get<Calendar[]>(this._calendarUrl + '?viewType=' + viewType + '&value=' + value)
      .pipe(
        catchError(err => this.handleError(err))
      );
  }

  postCalendar(calendar: Calendar[]) {
    return this.postCalendarWithOptions(calendar, {});
  }

  postCalendarWithResponse(calendar: Calendar[]) {
    return this.postCalendarWithOptions(calendar, {observe: 'response'});
  }

  private postCalendarWithOptions(calendar: Calendar[], options: any) {
    return this.http.post<Calendar[]>(this._calendarUrl, calendar, options)
      .pipe(
        catchError(err => this.handleError(err))
      );
  }

  postVacationRequest(request: PostRequest) {
    return this.postRequestWithTypeAndOptions(request, RequestTypes.VACATION, {});
  }

  postVacationRequestWithResponse(request: PostRequest) {
    return this.postRequestWithTypeAndOptions(request, RequestTypes.VACATION, {observe: 'response'});
  }

  postAuthorizationRequest(request: PostRequest) {
    return this.postRequestWithTypeAndOptions(request, RequestTypes.AUTHORIZATION, {});
  }

  postAuthorizationRequestWithResponse(request: PostRequest) {
    return this.postRequestWithTypeAndOptions(request, RequestTypes.AUTHORIZATION, {observe: 'response'});
  }

  private postRequestWithTypeAndOptions(request: PostRequest, type: string, options: any) {
    return this.http.post<PostRequest>(this._postRequestUrl + type, request, options)
      .pipe(
        catchError(err => this.handleError(err))
      );
  }

  postUserSettings(id: number, settings: PostSettings) {
    return this.postUserSettingsWithOptions(id, settings, {});
  }

  postUserSettingsWithResponse(id: number, settings: PostSettings) {
    return this.postUserSettingsWithOptions(id, settings, {observe: 'response'});
  }

  private postUserSettingsWithOptions(id: number, settings: PostSettings, options: any) {
    return this.http.post<PostSettings>(this._userUrl + id + '/settings', settings, options)
      .pipe(
        catchError(err => this.handleError(err))
      );
  }

  constructor(protected http: HttpClient) {
    super(http);
  }
}
