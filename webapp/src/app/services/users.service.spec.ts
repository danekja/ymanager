import {TestBed} from '@angular/core/testing';

import {UsersService} from './users.service';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {VacationRequest} from '../models/requests.model';
import {RequestStatus, VacationType} from '../enums/common.enum';
import {environment} from '../../environments/environment';

describe('UsersService', () => {
  let service: UsersService;
  let httpMock: HttpTestingController;
  const baseUrl: string = environment.apiUrl;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UsersService]
    });

    service = TestBed.get(UsersService);
    httpMock = TestBed.get(HttpTestingController);
  });
  afterEach(() => httpMock.verify());


  it('getAuthorizationRequests', () => {
    const dummyRequests = [
      { id: 1, firstName: 'Tomas', lastName: 'Novak', timestamp: '2019/05/24 21:55:11' },
      { id: 2, firstName: 'Tomas', lastName: 'Novak', timestamp: '2019/05/24 21:55:11' },
      { id: 3, firstName: 'Tomas', lastName: 'Novak', timestamp: '2019/05/24 21:55:11' },
      { id: 4, firstName: 'Tomas', lastName: 'Novak', timestamp: '2019/05/24 21:55:11' },
      { id: 5, firstName: 'Tomas', lastName: 'Novak', timestamp: '2019/05/24 21:55:11' }
    ];

    service.getAuthorizationRequests().subscribe((data: any) => {
      expect(data.length).toBe(5);
      expect(data[4].lastName).toBe('Novak');
      expect(data[4].firstName).toBe('Tomas');
      expect(data[4].id).toBe(5);
      expect(data[4].timestamp).toBeDefined();
    });

    const req = httpMock.expectOne(baseUrl + '/api/users/requests/authorization?');
    expect(req.request.method).toBe('GET');
    req.flush(dummyRequests);
  });

  it('getVacationRequests', () => {
    const dummyRequests = [
        {id: 1, firstName: 'Tomas', lastName: 'Novak', date: '2019/05/24', type: 'SICKDAY', timestamp: '2019/05/24 21:59:32'},
        { id: 2, firstName: 'Tomas', lastName: 'Novak', date: '2019/05/24', from: '09:30', to: '18:30', type: 'VACATION', timestamp: '2019/05/24 21:59:32' },
        {id: 3, firstName: 'Tomas', lastName: 'Novak', date: '2019/05/24', type: 'SICKDAY', timestamp: '2019/05/24 21:59:32'},
        { id: 4, firstName: 'Tomas', lastName: 'Novak', date: '2019/05/24', from: '09:30', to: '18:30', type: 'VACATION', timestamp: '2019/05/24 21:59:32' },
        {id: 5, firstName: 'Tomas', lastName: 'Novak', date: '2019/05/24', type: 'SICKDAY', timestamp: '2019/05/24 21:59:32'}
      ];
    service.getVacationRequests().subscribe((data: any) => {
      expect(data.length).toBe(5);

      expect(data[0].firstName).toBeDefined();
      expect(data[0].lastName).toBeDefined();

      expect(data[0].type).toBe(VacationType.SICKDAY);
      expect(data[0].to).toBeUndefined();
      expect(data[0].from).toBeUndefined();

      expect(data[1].type).toBe(VacationType.VACATION);
      expect(data[1].to).toBeDefined();
      expect(data[1].from).toBeDefined();
    });

    const req = httpMock.expectOne(baseUrl + '/api/users/requests/vacation?');
    expect(req.request.method).toBe('GET');
    req.flush(dummyRequests);
  });


  it('getAllUsers', () => {
    const dummyRequests = [
      {
        id: 1,
        firstName: 'Tomas',
        lastName: 'unknown',
        photo: 'https://st2.depositphotos.com/9223672/12056/v/950/depositphotos_120568236-stock-illustration-male-face-avatar-logo-template.jpg',
        calendar: [
          {
            id: 1,
            date: '2019/05/24',
            from: '09:00',
            to: '13:00',
            type: 'VACATION',
            status: 'ACCEPTED'
          },
          {
            id: 2,
            date: '2019/05/25',
            from: '09:00',
            to: '13:00',
            type: 'VACATION',
            status: 'ACCEPTED'
          }
        ]
      },
      {
        id: 2,
        firstName: 'Tomas',
        lastName: 'unknown',
        photo: 'https://st2.depositphotos.com/9223672/12056/v/950/depositphotos_120568236-stock-illustration-male-face-avatar-logo-template.jpg',
        calendar: [
          {
            id: 1,
            date: '2019/05/24',
            from: '09:00',
            to: '13:00',
            type: 'VACATION',
            status: 'ACCEPTED'
          },
          {
            id: 2,
            date: '2019/05/25',
            from: '09:00',
            to: '13:00',
            type: 'VACATION',
            status: 'ACCEPTED'
          }
        ]
      },
      {
        id: 3,
        firstName: 'Tomas',
        lastName: 'unknown',
        photo: 'https://st2.depositphotos.com/9223672/12056/v/950/depositphotos_120568236-stock-illustration-male-face-avatar-logo-template.jpg',
        calendar: [
          {
            id: 1,
            date: '2019/05/24',
            from: '09:00',
            to: '13:00',
            type: 'VACATION',
            status: 'ACCEPTED'
          },
          {
            id: 2,
            date: '2019/05/25',
            from: '09:00',
            to: '13:00',
            type: 'VACATION',
            status: 'ACCEPTED'
          }
        ]
      },
      {
        id: 4,
        firstName: 'Tomas',
        lastName: 'unknown',
        photo: 'https://st2.depositphotos.com/9223672/12056/v/950/depositphotos_120568236-stock-illustration-male-face-avatar-logo-template.jpg',
        calendar: [
          {
            id: 1,
            date: '2019/05/24',
            from: '09:00',
            to: '13:00',
            type: 'VACATION',
            status: 'ACCEPTED'
          },
          {
            id: 2,
            date: '2019/05/25',
            from: '09:00',
            to: '13:00',
            type: 'VACATION',
            status: 'ACCEPTED'
          }
        ]
      },
      {
        id: 5,
        firstName: 'Tomas',
        lastName: 'unknown',
        photo: 'https://st2.depositphotos.com/9223672/12056/v/950/depositphotos_120568236-stock-illustration-male-face-avatar-logo-template.jpg',
        calendar: [
          {
            id: 1,
            date: '2019/05/24',
            from: '09:00',
            to: '13:00',
            type: 'VACATION',
            status: 'ACCEPTED'
          },
          {
            id: 2,
            date: '2019/05/25',
            from: '09:00',
            to: '13:00',
            type: 'VACATION',
            status: 'ACCEPTED'
          }
        ]
      }
    ];
    service.getUsers().subscribe((data: any) => {
      expect(data.length).toBe(5);
      expect(data[0].id).toBe(1);
      expect(data[0].firstName).toBeDefined();
      expect(data[0].lastName).toBeDefined();
      expect(data[0].photo).toBeDefined();
      expect(data[0].calendar[0].type).toBe(VacationType.VACATION);
      expect(data[0].calendar[0].status).toBe(RequestStatus.ACCEPTED);
      expect(data[0].calendar[0].id).toBe(1);
      expect(data[0].calendar[1].id).toBe(2);
    });

    const req = httpMock.expectOne(baseUrl + '/api/users?');
    expect(req.request.method).toBe('GET');
    req.flush(dummyRequests);
  });

});
