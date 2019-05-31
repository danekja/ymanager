import {Injectable} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {registerLocaleData} from '@angular/common';
import localeEn from '@angular/common/locales/en';
import localeCs from '@angular/common/locales/cs';
import {Subject} from 'rxjs';
import {Languages} from '../enums/common.enum';

@Injectable({
  providedIn: 'root'
})
export class LocalizationService {
  readonly defaultLocale = 'cs';

  currentLocaleSubject: Subject<string>;

  private currentLocale = this.defaultLocale;

  constructor(private translate: TranslateService) {
    this.currentLocaleSubject = new Subject<string>();

    registerLocaleData(localeEn);
    registerLocaleData(localeCs);

    translate.setDefaultLang(this.defaultLocale);
  }

  switchLocale(locale: string): string {
    this.translate.use(locale);
    this.currentLocaleSubject.next(locale);
    this.currentLocale = locale;

    return this.getCurrentLanguage();
  }

  getCurrentLanguage(): Languages {
    switch (this.currentLocale) {
      case 'cs':
        return Languages.CZECH;
      case 'en':
      default:
        return Languages.ENGLISH;
    }
  }

  getCurrentLocale(): string {
    return this.currentLocale;
  }
}
