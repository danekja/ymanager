import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';

import {UserProfile} from '../models/user-profile.model';
import {Calendar, CalendarEdit, PostCalendar} from '../models/calendar.model';
import {BasicService} from './basic.service';
import {catchError} from 'rxjs/operators';
import {Languages, RequestStatus, RequestTypes} from '../enums/common.enum';
import {UserRequest} from '../models/post-requests.model';
import {PostUserSettings} from '../models/settings.model';

@Injectable({
  providedIn: 'root'
})
export class UserService extends BasicService { // dost podobny k usersService, mozna zmenit v rest api
  private _userUrl = this.baseUrl + '/api/user/';

  constructor(protected http: HttpClient) {
    super(http);
  }

  /**
   * Returns employee profile if the user making this call
   * is logged as admin
   * UserProfile.notification might be returned as string instead of date
   * @param id employee id
   */
  getEmployeeProfile(id: number) {
    return this.makeGetProfileApiCall(id.toString(), '');
  }

  /**
   * Overloaded version of getEmployeeProfile to filter profiles
   * by language
   * UserProfile.notification might be returned as string instead of date
   * @param id employee profile id
   * @param language language to filtery by
   */
  getEmployeeProfileWithLanguage(id: number, language: Languages) {
    return this.makeGetProfileApiCall(id.toString(), language);
  }

  /**
   * Returns profile of currently logged user
   * UserProfile.notification might be returned as string instead of date
   */
  getLoggedUserProfile() {
    return this.makeGetProfileApiCall('me', null);
  }

  /**
   * Returns profile of currently logged user filtered by language
   * UserProfile.notification might be returned as string instead of date
   * @param language filter profile by language
   */
  getLoggedUserProfileWithLanguage(language: Languages) {
    return this.makeGetProfileApiCall('me', language);
  }

  /**
   * Returns vacation and sick days from the given date
   * for logged user
   * @param from returns days from this date forward
   */
  getLoggedUserCalendar(from: Date) {
    return this.makeGetCalendarApiCall('me', from, null, null, null);
  }

  /**
   * Returns vacation and sick days from the given date
   * for logged user
   * @param from returns days from this date forward
   * @param to limit returned days, returns <from, to>
   * @param language filter by language
   * @param status filter by status
   */
  getLoggedUserCalendarWithOptions(from: Date, to: Date, language: Languages, status: RequestStatus) {
    return this.makeGetCalendarApiCall('me', from, to, language, status);
  }

  /**
   * Post user calendar using POST
   * @param calendar to be posted
   */
  postCalendar(calendar: PostCalendar) {
    return this.makePostCalendarApiCall(calendar, null);
  }

  /**
   * Post user calendar using POST with specified language
   * @param calendar to be posted
   * @param language specified language
   */
  postCalendarWithLanguage(calendar: PostCalendar, language: Languages) {
    return this.makePostCalendarApiCall(calendar, language);
  }

  /**
   * Put user settings with given id for the user
   * @param settings settings to be put
   */
  putUserSettings(settings: PostUserSettings) {
    return this.makePutUserSettingsApiCall(settings, null);
  }

  /**
   * Put user settings with given id for the user
   * @param settings settings to be put
   * @param language specified language
   */
  putUserSettingsWithLanguage(settings: PostUserSettings, language: Languages) {
    return this.makePutUserSettingsApiCall(settings, language);
  }

  /**
   * Accept or deny user request
   * @param request request to accept or deny
   * @param type request type
   */
  putUserRequest(request: UserRequest, type: RequestTypes) {
    return this.makePutUserRequestApiCall(request, type, null);
  }

  /**
   * Accept or deny user request
   * @param request request to accept or deny
   * @param type reqeust type
   * @param language specify language
   */
  putUserRequestWithLanguage(request: UserRequest, type: RequestTypes, language: Languages) {
    return this.makePutUserRequestApiCall(request, type, language);
  }

  /**
   * Edit calendar
   * @param calendarEdit calendar day to be edited
   * @param language specify language
   */
  putCalendarEdit(calendarEdit: CalendarEdit, language: Languages) {
    return this.makePutCalendarEditApiCall(calendarEdit, null);
  }

  /**
   * Delete calendar vacation day with given id
   * @param id calendar day id to be deleted
   * @param language specify language
   */
  deleteCalendar(id: number, language: Languages) {
    return this.makeDeleteCalendarApiCall(id, language);
  }

  /**
   * Získání profilu aktuálně přihlášeného uživatele nebo uživatele podle zadaného id.
   * GET /user/<{id} || me>/profile?[lang=<CZ,EN>]
   * @param id id of profile to get (number or 'me')
   * @param language filter by language
   */
  private makeGetProfileApiCall(id: string, language: string) {
    const httpParams: HttpParams = this.createParams({lang: language});

    const options = {params: httpParams};

    return this.http.get<UserProfile>(this._userUrl + id + '/profile', options)
      .pipe(
        catchError(err => this.handleError(err))
      );
  }

  /**
   * Získání dovolené a sick days v zadaném období. Pokud není zadán parameter “to” vrátí všechny dovolené a sick days od “from”. Navíc umožňuje filtrovat pomocí statusu schválení.
   * GET /user/<{id} || me>/calendar?[lang=<CZ,EN>]&from=yyyy/mm/dd, [to=yyyy/mm/dd], [status=<ACCEPTED, PENDING, REJECTED>]
   * @param id id of calendar to get (number or 'me')
   * @param from mandatory param
   * @param to upper limit of days
   * @param language filter by language
   * @param status filter by status
   */
  private makeGetCalendarApiCall(id: string, from: Date, to: Date, language: Languages, status: RequestStatus) {
    const fromString: string = from.getFullYear() + '/' + from.getMonth() + '/' + from.getDate();
    let toString: string;
    if (to != null) {
      toString = to.getFullYear() + '/' + to.getMonth() + '/' + to.getDate();
    }

    const httpParams: HttpParams = this.createParams({lang: language, from: fromString, to: toString, status});
    const options = {params: httpParams};

    return this.http.get<Calendar[]>(this._userUrl + id + '/calendar', options)
      .pipe(
        catchError(err => this.handleError(err))
      );
  }

  /**
   * Povolení nebo zamítnutí žádosti nebo “smazání“ uživatele (změna statusu na REJECTED)
   * PUT /user/requests?[lang=<CZ,EN>]&type=<VACATION, AUTHORIZATION>
   * @param request request to accept or reject
   * @param reqType request type
   * @param language specify language
   */
  private makePutUserRequestApiCall(request: UserRequest, reqType: RequestTypes, language: Languages) {
    const httpParams: HttpParams = this.createParams({type: reqType, lang: language});
    const options = {params: httpParams};

    return this.http.put<UserRequest>(this._userUrl + 'requests', request, options)
      .pipe(
        catchError(err => this.handleError(err))
      );
  }


  /**
   * Změna nastavení uživatele podle id
   * PUT /user/settings?[lang=<CZ,EN>]
   * @param settings setting to be set for given user
   * @param language specified language
   */
  private makePutUserSettingsApiCall(settings: PostUserSettings, language: Languages) {
    const httpParams: HttpParams = this.createParams({lang: language});
    const options = {params: httpParams};

    return this.http.put<PostUserSettings>(this._userUrl + 'settings', settings, options)
      .pipe(
        catchError(err => this.handleError(err))
      );
  }

  /**
   * Vytvoření nové dovolené nebo sick day
   * POST /user/calendar/create?[lang=<CZ,EN>]
   * @param calendar calendar to be posted
   * @param language specified language
   */
  private makePostCalendarApiCall(calendar: PostCalendar, language: Languages) {
    const httpParams: HttpParams = this.createParams({lang: language});
    const options = {params: httpParams};

    return this.http.post<PostCalendar>(this._userUrl + 'calendar/create', calendar, options)
      .pipe(
        catchError(err => this.handleError(err))
      );

  }

  /**
   * Smazání dovolené nebo sickday podle jejího id
   * DELETE /calendar/delete?[lang=<CZ,EN>]
   * @param id id of calendar to delete
   * @param language specify language
   */
  private makeDeleteCalendarApiCall(id: number, language: Languages) {
    const httpParams: HttpParams = this.createParams({lang: language});
    const options = {params: httpParams};

    return this.http.delete(this._userUrl + 'calendar/' + id + '/delete', options)
      .pipe(
        catchError(err => this.handleError(err))
      );
  }

  /**
   * Editace dovolené nebo sickday podle jejího id
   * PUT /calendar/edit?[lang=<CZ,EN>]
   * @param calendarEdit calendar to edit
   * @param language specify language
   */
  private makePutCalendarEditApiCall(calendarEdit: CalendarEdit, language: Languages) {
    const httpParams: HttpParams = this.createParams({lang: language});
    const options = {params: httpParams};

    return this.http.put<CalendarEdit>(this._userUrl + 'calendar/edit', calendarEdit, options)
      .pipe(
        catchError(err => this.handleError(err))
      );
  }
}
