import {Component, OnInit} from '@angular/core';
import {UserBasicInformation} from '../models/user-basic-information.model';
import {UsersService} from '../services/users.service';
import {VacationType} from '../enums/common.enum';
import {MatDialog} from '@angular/material';
import {EditEmployeeDialogComponent} from './edit-employee-dialog/edit-employee-dialog.component';
import {DayInfo, User} from './user.model';
import {AuthorizationRequest} from '../models/requests.model';

const daysOfWeek: string[] = [
  'po',
  'ut',
  'st',
  'ct',
  'pa',
  'so',
  'ne',
];

@Component({
  selector: 'app-employees-list',
  templateUrl: './employees-list.component.html',
  styleUrls: ['./employees-list.component.sass']
})
export class EmployeesListComponent implements OnInit {
  days: string[];
  private _users: User[];
  private _dates: Date[];
  private _employeesBasicInformation: UserBasicInformation[] = [];
  readonly _todayDate: Date = new Date();

  constructor(private usersService: UsersService, public dialog: MatDialog) {
    this.generateDays();
    this.generateDates();
    this.editDates();
  }

  openDialog(user: User): void {
    this.dialog.open(EditEmployeeDialogComponent, {
     data: user,
   });
  }

  private generateDays(): void {
    this.days = [];

    for (let i = this._todayDate.getDay() - 8; i < this._todayDate.getDay() + 7; i++) {
      this.days.push(daysOfWeek[((i % 7) + 7) % 7]);
    }
  }

  private generateDates(): void {
    this._dates = [];
    for (let i = 0; i < 15; i++) {
      this._dates.push(new Date());
    }
  }

  private editDates(): void {
    let j = 0;
    let date: Date;

    for (let i = -7; i < 8; i++) {
      date = this._dates[j++];
      date.setDate(date.getDate() + i);
    }
  }

  private mapUsers(): void {
    let user: User;
    this._users = [];

    for (const info of this._employeesBasicInformation) {
      user = new User();
      user.name = info.firstName + ' ' + info.lastName;
      user.id = info.id;
      user.imageLink = info.photo;
      user.dates = this.mapDays(info);
      this._users.push(user);
    }
  }

  private mapDays(user: UserBasicInformation): DayInfo[] {
    const dayInfo: DayInfo[] = [];

    for (const date of this._dates) {
      const day: DayInfo = new DayInfo();
      day.type = VacationType.NONE;

      for (const vacationDay of user.calendar) {
        const vacationDate: Date = new Date(vacationDay.date);

        if (vacationDate.getDate() === date.getDate()) {
          day.type = vacationDay.type;
        }
      }

      day.date = date;
      dayInfo.push(day);
    }

    return dayInfo;
  }

  ngOnInit() {
    this.usersService.getAuthorizedUsers()
      .subscribe((data: UserBasicInformation[]) => {
        for (const entry of data) {
          this._employeesBasicInformation.push(entry);
        }
        this.mapUsers();
        console.log(this._users);
      });


    let authorization: AuthorizationRequest[];
    this.usersService.getAuthorizationRequests()
      .subscribe((data: AuthorizationRequest[]) => {
        authorization = { ...data};

        console.log(authorization);
      });
  }

}
