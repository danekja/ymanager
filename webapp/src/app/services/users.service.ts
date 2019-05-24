import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BasicService} from './basic.service';
import {catchError} from 'rxjs/operators';

import {UserBasicInformation} from '../models/user-basic-information.model';
import {AuthorizationRequest, VacationRequest} from '../models/requests.model';
import {Languages, ProfileStatus} from '../enums/common.enum';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsersService extends BasicService {
  private _usersUrl = this.baseUrl + '/api/users?';
  private _statusPrefix = 'status=';
  private _languagePrefix = 'language=';
  private _vacationRequestsUrl = this.baseUrl + '/api/users/requests/vacation?';
  private _authorizationRequestsUrl = this.baseUrl + '/api/users/requests/authorization?';

  /**
   * Returns all authorized users
   */
  getAuthorizedUsers() {
    return this.makeUsersApiCall(ProfileStatus.AUTHORIZED, '');
  }

  /**
   * Returns all authorized users with specified language
   * overloaded version of getAuthorizedUsers
   * @param language filter users based on language
   */
  getAuthorizedUsersWithLanguage(language: Languages) {
    return this.makeUsersApiCall(ProfileStatus.AUTHORIZED, language);
  }

  /**
   * Returns all pending users
   */
  getPendingUsers() {
    return this.makeUsersApiCall(ProfileStatus.PENDING, '');
  }

  /**
   * Returns all pending users with specified language
   * overloaded version of getPendingUsers
   * @param language filter users based on language
   */
  getPendingUsersWithLanguage(language: Languages) {
    return this.makeUsersApiCall(ProfileStatus.PENDING, language);
  }

  /**
   * Return all rejected users
   */
  getRejectedUsers() {
    return this.makeUsersApiCall(ProfileStatus.REJECTED, '');
  }

  /**
   * Returns all rejected users with specified language
   * overloaded version of getRejectedUsers
   * @param language filter users based on language
   */
  getRejectedUsersWithLanguage(language: Languages) {
    return this.makeUsersApiCall(ProfileStatus.REJECTED, language);
  }

 /**
  * Default api call which returns all users in the database
  * regardless of language and status
  */
  getUsers() {
    return this.makeUsersApiCall('', '');
  }

  /**
   * Returns all vacation requests
   */
  getVacationRequests() {
    return this.makeVacationRequestsApiCall('');
  }

  /**
   * Returns vacations filtered by language
   * @param language filter by passed language
   */
  getVacationRequestsWithLanguage(language: Languages) {
    return this.makeVacationRequestsApiCall(language);
  }


  /**
   * Returns all authorization requests
   */
  getAuthorizationRequests() {
    return this.makeAuthorizationRequestsApiCall('');
  }


  /**
   * Returns authorization requests filtered by language
   * @param language filter by passed language
   */
  getAuthorizationRequestsWithLanguage(language: Languages) {
    return this.makeAuthorizationRequestsApiCall(language);
  }

  private makeAuthorizationRequestsApiCall(language: string) {
    const apiUrl: string = this.createApiUrl(this._authorizationRequestsUrl, '', language);

    return this.http.get<AuthorizationRequest[]>(apiUrl)
      .pipe(
        catchError(err => this.handleError(err))
      );
  }

  private makeVacationRequestsApiCall(language: string) {
    const apiUrl: string = this.createApiUrl(this._vacationRequestsUrl, '', language);

    return this.http.get<VacationRequest[]>(apiUrl)
      .pipe(
        catchError(err => this.handleError(err))
      );
  }

  private makeUsersApiCall(status: string, language: string): Observable<UserBasicInformation[]> {
    const apiUrl: string = this.createApiUrl(this._usersUrl, status, language);

    return this.http.get<UserBasicInformation[]>(apiUrl)
      .pipe(
        catchError(err => this.handleError(err))
      );
  }

  private createApiUrl(base: string, status: string, language: string): string {
    let apiUrl = base;

    if (status.length > 0) {
      apiUrl += this._statusPrefix + status + '&';
    }
    if (language.length > 0) {
      apiUrl += this._languagePrefix + language;
    }

    return apiUrl;
  }

  constructor(protected http: HttpClient) {
    super(http);
  }

}
