import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError } from 'rxjs/operators';

import { BasicService } from './basic.service';
import { Settings } from '../models/settings.model';

@Injectable({
  providedIn: 'root'
})
export class SettingsService extends BasicService {
  defaultSettingsUrl = this.baseUrl + '/settings/default';

  getDefaultSettings() {
    return this.http.get<Settings>(this.defaultSettingsUrl)
      .pipe(
        catchError(err => this.handleError(err))
      );
  }

  postDefaultSettingsWithResponse(settings: Settings) {
    return this.postDefaultSettingsWithOptions(settings, {observe: 'response'});
  }

  postDefaultSettings(settings: Settings) {
    return this.postDefaultSettingsWithOptions(settings, {});
  }

  private postDefaultSettingsWithOptions(settings: Settings, options: any) {
    return this.http.post<Settings>(this.defaultSettingsUrl, settings, options)
      .pipe(
        catchError(err => this.handleError(err))
      );
  }

  constructor(protected http: HttpClient) {
    super(http);
  }
}
