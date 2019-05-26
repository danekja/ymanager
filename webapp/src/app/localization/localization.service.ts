import {Injectable} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {registerLocaleData} from '@angular/common';
import localeEn from '@angular/common/locales/en';
import localeCs from '@angular/common/locales/cs';
import {Subject} from 'rxjs';
import {Languages} from "../enums/common.enum";

@Injectable({
  providedIn: 'root'
})
export class LocalizationService {
  readonly defaultLanguage = 'en';

  currentLanguageSubject: Subject<string>;

  private currentLanguage = this.defaultLanguage;

  constructor(private translate: TranslateService) {
    this.currentLanguageSubject = new Subject<string>();

    registerLocaleData(localeEn);
    registerLocaleData(localeCs);

    translate.setDefaultLang(this.defaultLanguage);
  }

  switchLanguage(lang: string) {
    this.translate.use(lang);
    this.currentLanguageSubject.next(lang);
    this.currentLanguage = lang;
  }

  getCurrentLanguage(): Languages {
    switch (this.currentLanguage) {
      case 'cs':
        return Languages.CZECH;
      case 'en':
      default:
        return Languages.ENGLISH;
    }
  }
}
