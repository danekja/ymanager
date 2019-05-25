import {TestBed} from '@angular/core/testing';

import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {environment} from '../../environments/environment';
import {SettingsService} from './settings.service';
import {Languages} from '../enums/common.enum';

describe('SettingsService', () => {
  let service: SettingsService;
  let httpMock: HttpTestingController;
  const baseUrl: string = environment.apiUrl;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [SettingsService]
    });

    service = TestBed.get(SettingsService);
    httpMock = TestBed.get(HttpTestingController);
  });
  afterEach(() => httpMock.verify());

  it('getDefaultSettings', () => {
    const dummyData = {
      sickdayCount: 3,
      notification: '2019/05/25 14:41:31'
    };

    service.getDefaultSettings().subscribe((data: any) => {
      expect(data.sickdayCount).toBeDefined();
      expect(data.sickdayCount).toBe(3);
      expect(data.notification).toBeDefined();
    });

    const req = httpMock.expectOne(baseUrl + '/api/settings');
    expect(req.request.method).toBe('GET');
    req.flush(dummyData);
  });

  it('getDefaultSettingsWithLanguage', () => {
    const dummyData = {
      sickdayCount: 3,
      notification: '2019/05/25 14:41:31'
    };

    service.getDefaultSettingsWithLanguage(Languages.ENGLISH).subscribe((data: any) => {
      expect(data.sickdayCount).toBeDefined();
      expect(data.sickdayCount).toBe(3);
      expect(data.notification).toBeDefined();
    });

    const req = httpMock.expectOne(baseUrl + '/api/settings?lang=EN');
    expect(req.request.method).toBe('GET');
    req.flush(dummyData);
  });


  it('postDefaultSettings', () => {
    const dummyAnswer = {
      answer: 'ok',
    };

    service.postDefaultSettings({sickdayCount: 3, notification: '2019/04/25 18:23:36'})
      .subscribe((data: any) => expect(data.answer).toBe('ok'));

    const req = httpMock.expectOne(baseUrl + '/api/settings');
    expect(req.request.method).toBe('POST');
    req.flush(dummyAnswer);
  });

  it('postDefaultSettingsWithLanguage', () => {
    const dummyAnswer = {
      answer: 'ok',
    };

    service.postDefaultSettingsWithLanguage({sickdayCount: 3, notification: '2019/04/25 18:23:36'}, Languages.ENGLISH)
      .subscribe((data: any) => expect(data.answer).toBe('ok'));

    const req = httpMock.expectOne(baseUrl + '/api/settings?lang=EN');
    expect(req.request.method).toBe('POST');
    req.flush(dummyAnswer);
  });

});
