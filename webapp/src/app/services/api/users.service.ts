import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {BasicService} from './basic.service';
import {catchError} from 'rxjs/operators';

import {AuthorizationRequest, VacationRequest} from '../../models/requests.model';
import {Languages, ProfileStatus} from '../../enums/common.enum';
import {Observable} from 'rxjs';
import {UserBasicInformation} from '../../models/user.model';
import {MatSnackBar} from '@angular/material';

@Injectable({
  providedIn: 'root'
})
export class UsersService extends BasicService {
  private _usersUrl = this.baseUrl + '/api/users';

  constructor(protected http: HttpClient, protected snackBar: MatSnackBar) {
    super(http, snackBar);
  }

  /**
   * Returns all authorized users
   */
  getAuthorizedUsers() {
    return this.makeGetUsersApiCall(null, ProfileStatus.AUTHORIZED);
  }

  /**
   * Returns all authorized users with specified language
   * overloaded version of getAuthorizedUsers
   * @param language filter users based on language
   */
  getAuthorizedUsersWithLanguage(language: Languages) {
    return this.makeGetUsersApiCall(language, ProfileStatus.AUTHORIZED);
  }

  /**
   * Returns all pending users
   */
  getPendingUsers() {
    return this.makeGetUsersApiCall(null, ProfileStatus.PENDING);
  }

  /**
   * Returns all pending users with specified language
   * overloaded version of getPendingUsers
   * @param language filter users based on language
   */
  getPendingUsersWithLanguage(language: Languages) {
    return this.makeGetUsersApiCall(language, ProfileStatus.PENDING);
  }

  /**
   * Return all rejected users
   */
  getRejectedUsers() {
    return this.makeGetUsersApiCall(null, ProfileStatus.REJECTED);
  }

  /**
   * Returns all rejected users with specified language
   * overloaded version of getRejectedUsers
   * @param language filter users based on language
   */
  getRejectedUsersWithLanguage(language: Languages) {
    return this.makeGetUsersApiCall(language, ProfileStatus.REJECTED);
  }

 /**
  * Default api call which returns all users in the database
  * regardless of language and status
  */
  getUsers() {
    return this.makeGetUsersApiCall();
  }

  /**
   * Returns all vacation requests specified by status, if status
   * is not specified, returns all vacation requests
   * @param status optional vacation request status
   */
  getVacationRequests(status?: string) {
    return this.makeGetVacationRequestsApiCall( null, status);
  }

  /**
   * Returns vacations filtered by language
   * @param language filter by passed language
   * @param status optionalvacation request status
   */
  getVacationRequestsWithLanguage(language: Languages, status?: string) {
    return this.makeGetVacationRequestsApiCall(language, status);
  }


  /**
   * Returns all authorization requests
   * @param status optional authorization request status
   */
  getAuthorizationRequests(status?: string) {
    return this.makeGetAuthorizationRequestsApiCall(null, status);
  }


  /**
   * Returns authorization requests filtered by language
   * @param language filter by passed language
   * @param status optional authorization request status
   */
  getAuthorizationRequestsWithLanguage(language: Languages, status?: string) {
    return this.makeGetAuthorizationRequestsApiCall(language, status);
  }

  /**
   * Získání žádostí o autorizaci všech uživatelů s možností filtrace pomocí úrovně schválení.
   * GET /users/requests/authorization?[lang=<CZ,EN>]&[status=<ACCEPTED, PENDING, REJECTED>
   * @param language filter by language
   * @param status optional authorization request status
   */
  private makeGetAuthorizationRequestsApiCall(language?: string, statusReq?: string) {
    const httpParams: HttpParams = this.createParams({lang: language, status: statusReq});
    const options = {params: httpParams};

    return this.http.get<AuthorizationRequest[]>(this._usersUrl + '/requests/authorization', options)
      .pipe(
        catchError(err => this.handleError(err))
      );
  }

  /**
   * Získání žádostí o dovolené a sick days všech uživatelů s možností filtrace pomocí úrovně schválení.
   * GET /users/requests/vacation? [lang=<CZ,EN>]&[status=<ACCEPTED, PENDING, REJECTED>]
   * @param language filter by language
   * @param status vacation request status
   */
  private makeGetVacationRequestsApiCall(language?: string, statusReq?: string) {
    const httpParams: HttpParams = this.createParams({lang: language, status: statusReq});
    const options = {params: httpParams};

    return this.http.get<VacationRequest[]>(this._usersUrl + '/requests/vacation', options)
      .pipe(
        catchError(err => this.handleError(err))
      );
  }

  /**
   * Získání všech uživatelů s možností filtrace pomocí statusu
   * GET /users?[lang=<CZ,EN>]&[status=<ACCEPTED, PENDING, REJECTED>]
   * @param status filter by status
   * @param language filter by language
   */
  private makeGetUsersApiCall(language?: string, status?: string): Observable<UserBasicInformation[]> {
    const httpParams: HttpParams = this.createParams({lang: language, status});
    const options = {params: httpParams};

    return this.http.get<UserBasicInformation[]>(this._usersUrl, options)
      .pipe(
        catchError(err => this.handleError(err))
      );
  }

}
