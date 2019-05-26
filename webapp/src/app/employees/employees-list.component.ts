import {Component, OnInit} from '@angular/core';
import {UsersService} from '../services/users.service';
import {VacationType} from '../enums/common.enum';
import {MatDialog} from '@angular/material';
import {EditEmployeeDialogComponent} from './edit-employee-dialog/edit-employee-dialog.component';
import {DayInfo, User} from './user.model';
import {UserBasicInformation} from '../models/user.model';

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


  /**
   * Map from UserBasicInformation model to inner class model User
   */
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

  /**
   * Creates array of days with information about the user's
   * vacation and sick days in range 7 days before and 7 days after
   * @param user one user row with
   */
  private mapDays(user: UserBasicInformation): DayInfo[] {
    const dayInfo: DayInfo[] = [];

    for (const date of this._dates) {
      const day: DayInfo = new DayInfo();
      day.type = VacationType.NONE;

      for (const calendarDay of user.calendar) {
        if (new Date(calendarDay.date).getDate() === date.getDate()) {
          day.type = calendarDay.type;
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
        this._employeesBasicInformation = data;
        this.mapUsers();
      });

    // const calendar: PostCalendar = { date: '1999/10/10', from: '15:00', to: '17:00', type: VacationType.VACATION };
    // this.userService.postCalendar(calendar)
    //   .subscribe((data: any) => console.log(data));

    // const settings: PostUserSettings = {
    //   id: 1,
    //   role: UserType.EMPLOYEE,
    //   sickdayCount: 1,
    //   vacationCount: 1,
    // };
    //
    // this.userService.putUserSettings(settings)
    //   .subscribe((data: any) => console.log(data));

  }

}
