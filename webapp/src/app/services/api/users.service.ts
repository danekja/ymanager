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
    return this.makeGetUsersApiCall(ProfileStatus.AUTHORIZED, null);
  }

  /**
   * Returns all authorized users with specified language
   * overloaded version of getAuthorizedUsers
   * @param language filter users based on language
   */
  getAuthorizedUsersWithLanguage(language: Languages) {
    return this.makeGetUsersApiCall(ProfileStatus.AUTHORIZED, language);
  }

  /**
   * Returns all pending users
   */
  getPendingUsers() {
    return this.makeGetUsersApiCall(ProfileStatus.PENDING, null);
  }

  /**
   * Returns all pending users with specified language
   * overloaded version of getPendingUsers
   * @param language filter users based on language
   */
  getPendingUsersWithLanguage(language: Languages) {
    return this.makeGetUsersApiCall(ProfileStatus.PENDING, language);
  }

  /**
   * Return all rejected users
   */
  getRejectedUsers() {
    return this.makeGetUsersApiCall(ProfileStatus.REJECTED, null);
  }

  /**
   * Returns all rejected users with specified language
   * overloaded version of getRejectedUsers
   * @param language filter users based on language
   */
  getRejectedUsersWithLanguage(language: Languages) {
    return this.makeGetUsersApiCall(ProfileStatus.REJECTED, language);
  }

 /**
  * Default api call which returns all users in the database
  * regardless of language and status
  */
  getUsers() {
    return this.makeGetUsersApiCall(null, null);
  }

  /**
   * Returns all vacation requests
   */
  getVacationRequests() {
    return this.makeGetVacationRequestsApiCall(null);
  }

  /**
   * Returns vacations filtered by language
   * @param language filter by passed language
   */
  getVacationRequestsWithLanguage(language: Languages) {
    return this.makeGetVacationRequestsApiCall(language);
  }


  /**
   * Returns all authorization requests
   */
  getAuthorizationRequests() {
    return this.makeGetAuthorizationRequestsApiCall(null);
  }


  /**
   * Returns authorization requests filtered by language
   * @param language filter by passed language
   */
  getAuthorizationRequestsWithLanguage(language: Languages) {
    return this.makeGetAuthorizationRequestsApiCall(language);
  }

  /**
   * Získání žádostí o autorizaci všech uživatelů s možností filtrace pomocí úrovně schválení.
   * GET /users/requests/authorization?[lang=<CZ,EN>]&[status=<ACCEPTED, PENDING, REJECTED>
   * @param language filter by language
   */
  private makeGetAuthorizationRequestsApiCall(language: string) {
    const httpParams: HttpParams = this.createParams({lang: language});
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
   */
  private makeGetVacationRequestsApiCall(language: string) {
    const httpParams: HttpParams = this.createParams({lang: language});
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
  private makeGetUsersApiCall(status: string, language: string): Observable<UserBasicInformation[]> {
    const httpParams: HttpParams = this.createParams({lang: language, status});
    const options = {params: httpParams};

    return this.http.get<UserBasicInformation[]>(this._usersUrl, options)
      .pipe(
        catchError(err => this.handleError(err))
      );
  }

}
