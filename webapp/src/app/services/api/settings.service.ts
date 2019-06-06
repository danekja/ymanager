import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import { catchError } from 'rxjs/operators';

import { BasicService } from './basic.service';
import { Settings } from '../../models/settings.model';
import {Languages} from '../../enums/common.enum';
import {MatSnackBar} from '@angular/material';
import {TranslateService} from '@ngx-translate/core';

@Injectable({
  providedIn: 'root'
})
export class SettingsService extends BasicService {
  defaultSettingsUrl = this.baseUrl + '/api/settings';

  constructor(protected http: HttpClient, protected snackBar: MatSnackBar, protected translateService: TranslateService) {
    super(http, snackBar, translateService);
  }

  /**
   * Returns default application settings
   * with sickday count and notification
   */
  getDefaultSettings() {
    return this.makeGetSettingsApiCall(null);
  }

  /**
   * Returns default application settings
   * with sickday count and notification
   * @param language filter by language
   */
  getDefaultSettingsWithLanguage(language: Languages) {
    return this.makeGetSettingsApiCall(language);
  }

  /**
   * Získání aktuálně použitého výchozího nastavení v aplikaci
   * GET /setttings?[lang=<CZ,EN>]
   * @param language filter with language
   */
  private makeGetSettingsApiCall(language: string) {
    const httpParams: HttpParams = this.createParams({lang: language});
    const options = {params: httpParams};

    return this.http.get<Settings>(this.defaultSettingsUrl, options)
      .pipe(
        catchError(err => this.handleError(err))
      );
  }

  postDefaultSettings(settings: Settings) {
    return this.postDefaultSettingsWithOptions(settings, null);
  }

  postDefaultSettingsWithLanguage(settings: Settings, language: Languages) {
    return this.postDefaultSettingsWithOptions(settings, language);
  }

  private postDefaultSettingsWithOptions(settings: Settings, language: Languages) {
    const httpParams: HttpParams = this.createParams({lang: language});
    const options = {params: httpParams};

    return this.http.post<Settings>(this.defaultSettingsUrl, settings, options)
      .pipe(
        catchError(err => this.handleError(err))
      );
  }
}
