import {TestBed} from '@angular/core/testing';

import {UserService} from '../api/user.service';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {Languages, RequestStatus, UserType, VacationType} from '../../enums/common.enum';
import {environment} from '../../../environments/environment';

describe('UsersService', () => {
  let service: UserService;
  let httpMock: HttpTestingController;
  const baseUrl: string = environment.apiUrl;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UserService]
    });

    service = TestBed.get(UserService);
    httpMock = TestBed.get(HttpTestingController);
  });
  afterEach(() => httpMock.verify());

  it('getLoggedUser', () => {
    const dummyData = {
      id: 1,
      firstName: 'Tomas',
      lastName: 'Novak',
      photo: 'https://st2.depositphotos.com/9223672/12056/v/950/depositphotos_120568236-stock-illustration-male-face-avatar-logo-template.jpg',
      vacationCount: 8.5,
      sickDayCount: 3,
      status: 'ACCEPTED',
      role: 'EMPLOYER',
      notification: '2000/12/01 09:00:00'
    };


    service.getLoggedUserProfile().subscribe((data: any) => {
      expect(data.id).toBe(1);
      expect(data.vacationCount).toBe(8.5);
      expect(data.sickDayCount).toBe(3);
      expect(data.status).toBe(RequestStatus.ACCEPTED);
      expect(data.role).toBe(UserType.EMPLOYER);
      expect(data.notification).toBeDefined();
    });

    const req = httpMock.expectOne(baseUrl + '/api/user/me/profile');
    expect(req.request.method).toBe('GET');
    req.flush(dummyData);
  });

  it('getLoggedUserWithLanguage', () => {
    const dummyData = {
      id: 1,
      firstName: 'Tomas',
      lastName: 'Novak',
      photo: 'https://st2.depositphotos.com/9223672/12056/v/950/depositphotos_120568236-stock-illustration-male-face-avatar-logo-template.jpg',
      vacationCount: 8.5,
      sickDayCount: 3,
      status: 'ACCEPTED',
      role: 'EMPLOYER',
      notification: '2000/12/01 09:00:00'
    };


    service.getLoggedUserProfileWithLanguage(Languages.ENGLISH).subscribe((data: any) => {
      expect(data.id).toBe(1);
      expect(data.vacationCount).toBe(8.5);
      expect(data.sickDayCount).toBe(3);
      expect(data.status).toBe(RequestStatus.ACCEPTED);
      expect(data.role).toBe(UserType.EMPLOYER);
      expect(data.notification).toBeDefined();
    });

    const req = httpMock.expectOne(baseUrl + '/api/user/me/profile?lang=EN');
    expect(req.request.method).toBe('GET');
    req.flush(dummyData);
  });

  it('getLoggedUserCalendar', () => {
    const dummyData = [
      {id: 1, date: '2000/10/10', from: '09:00', to: '13:00', type: 'VACATION'},
      {id: 2, date: '2000/10/11', type: 'SICKDAY'},
      {id: 3, date: '2000/10/12', from: '09:00', to: '13:00', type: 'VACATION'},
      {id: 4, date: '2000/10/13', type: 'SICKDAY'},
      {id: 5, date: '2000/10/14', from: '09:00', to: '13:00', type: 'VACATION'}
    ];

    service.getLoggedUserCalendar(new Date(1995, 10, 25))
      .subscribe((data: any) => {
        expect(data.length).toBe(5);
        expect(data[1].type).toBe(VacationType.SICKDAY);
        expect(data[2].type).toBe(VacationType.VACATION);
        expect(data[2].from).toBeDefined();
        expect(data[2].to).toBeDefined();
      });

    const req = httpMock.expectOne(baseUrl + '/api/user/me/calendar?from=1995/10/25');
    expect(req.request.method).toBe('GET');
    req.flush(dummyData);
  });

  it('getLoggedUserCalendarWithOptions', () => {
    const dummyData = [
      {id: 1, date: '2000/10/10', from: '09:00', to: '13:00', type: 'VACATION', status: 'ACCEPTED'},
      {id: 2, date: '2000/10/11', type: 'SICKDAY', status: 'ACCEPTED'},
      {id: 3, date: '2000/10/12', from: '09:00', to: '13:00', type: 'VACATION', status: 'ACCEPTED'},
      {id: 4, date: '2000/10/13', type: 'SICKDAY', status: 'ACCEPTED'},
      {id: 5, date: '2000/10/14', from: '09:00', to: '13:00', type: 'VACATION', status: 'ACCEPTED'}
    ];

    service.getLoggedUserCalendarWithOptions(new Date(1995, 10, 25), null, null, RequestStatus.ACCEPTED)
      .subscribe((data: any) => {
        expect(data.length).toBe(5);
        expect(data[0].status).toBeDefined();
        expect(data[0].status).toBe(RequestStatus.ACCEPTED);
        expect(data[1].status).toBe(RequestStatus.ACCEPTED);
        expect(data[2].status).toBe(RequestStatus.ACCEPTED);
        expect(data[3].status).toBe(RequestStatus.ACCEPTED);
        expect(data[4].status).toBe(RequestStatus.ACCEPTED);
      });

    const req = httpMock.expectOne(baseUrl + '/api/user/me/calendar?from=1995/10/25&status=ACCEPTED');
    expect(req.request.method).toBe('GET');
    req.flush(dummyData);
  });
});
