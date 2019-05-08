import {Component, OnInit} from '@angular/core';
import {UserBasicInformation} from '../models/user-basic-information.model';
import {UserProfile} from '../models/user-profile.model';
import {UsersService} from '../services/users.service';
import {UserService} from '../services/user.service';
import {Requests} from '../models/requests.model';
import {Calendar} from '../models/calendar.model';
import {SettingsService} from '../services/settings.service';
import {Settings} from '../models/settings.model';
import {PostRequest, PostSettings} from '../models/post-requests.model';
import {RequestStatus, TimeUnit, UserType} from '../enums/common.enum';

@Component({
  selector: 'app-employees-list',
  templateUrl: './employees-list.component.html',
  styleUrls: ['./employees-list.component.sass']
})
export class EmployeesListComponent implements OnInit {

  employeesBasicInformation: UserBasicInformation[];
  employeeProfile: UserProfile;
  requests: Requests;
  calendars: Calendar[];
  settings: Settings;

  doSomethingFancy(): void {
    console.log('klikam');
    this.settingsService.postDefaultSettingsWithResponse(this.settings)
      .subscribe(resp => console.log(resp));

    this.userService.postCalendarWithResponse(this.calendars)
      .subscribe(resp => console.log(resp));

    const request: PostRequest = {
      id: 10,
      status: RequestStatus.ACCEPTED,
    };
    this.userService.postAuthorizationRequestWithResponse(request)
      .subscribe(resp => console.log(resp));

    const settings: PostSettings = {
      role: UserType.EMPLOYEE,
      sickday: {
        unit: TimeUnit.DAY,
        value: 10,
      },
      vacation: {
        unit: TimeUnit.DAY,
        value: 10,
      },
    };

    this.userService.postUserSettingsWithResponse(1, settings)
      .subscribe(resp => console.log(resp));
  }

  constructor(private usersService: UsersService,
              private userService: UserService,
              private settingsService: SettingsService) {
  }

  ngOnInit() {
    this.usersService.getAuthorizedUsers()
      .subscribe((data: UserBasicInformation[]) => this.employeesBasicInformation = { ...data});

    this.userService.getEmployeeProfile(1)
      .subscribe((data: UserProfile) => this.employeeProfile = { ...data});

    this.usersService.getAuthorizationRequests()
      .subscribe((data: Requests) => this.requests = { ...data});

    this.userService.getMonthlyCalendar(1)
      .subscribe((data: Calendar[]) => this.calendars = { ...data});

    this.settingsService.getDefaultSettings()
      .subscribe((data: Settings) => this.settings = { ...data});
  }

}
