import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { registerLocaleData } from '@angular/common';
import localeEn from '@angular/common/locales/en';
import localeCs from '@angular/common/locales/cs';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LocalizationService {
  readonly defaultLanguage = 'en';

  currentLanguage: Subject<string>;

  constructor(private translate: TranslateService) {
    this.currentLanguage = new Subject<string>();

    registerLocaleData(localeEn);
    registerLocaleData(localeCs);

    translate.setDefaultLang(this.defaultLanguage);
  }

  switchLanguage(lang: string) {
    this.translate.use(lang);
    this.currentLanguage.next(lang);
  }
}
