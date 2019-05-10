import {Component, OnInit} from '@angular/core';
import {UserBasicInformation} from '../models/user-basic-information.model';
import {UsersService} from '../services/users.service';
import {VacationType} from '../enums/common.enum';

class DayInfo {
  date: Date;
  type: VacationType;
}

class User {
  id: number;
  name: string;
  dates: DayInfo[];
}

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
  private users: User[];
  private days: string[];
  private dates: Date[];
  private employeesBasicInformation: UserBasicInformation[] = [];
  readonly todayDate: Date = new Date();

  constructor(private usersService: UsersService) {
    this.generateDays();
    this.generateDates();
    this.editDates();
  }

  private generateDays(): void {
    this.days = [];

    for (let i = this.todayDate.getDay() - 8; i < this.todayDate.getDay() + 7; i++) {
      this.days.push(daysOfWeek[((i % 7) + 7) % 7]);
    }
  }

  private generateDates() {
    this.dates = [];
    for (let i = 0; i < 15; i++) {
      this.dates.push(new Date());
    }
  }

  private editDates(): void {
    let j = 0;
    let date: Date;

    for (let i = -7; i < 8; i++) {
      date = this.dates[j++];
      date.setDate(date.getDate() + i);
    }
  }

  private mapUsers(): void {
    let user: User;
    this.users = [];

    for (const info of this.employeesBasicInformation) {
      user = new User();
      user.name = info.name.first + ' ' + info.name.last;
      user.id = info.id;
      user.dates = this.mapDays(info);
      this.users.push(user);
    }
  }

  private mapDays(user: UserBasicInformation): DayInfo[] {
    const dayInfo: DayInfo[] = [];

    for (const date of this.dates) {
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
          this.employeesBasicInformation.push(entry);
        }
        this.mapUsers();
        console.log(this.users);
      });
  }

}
