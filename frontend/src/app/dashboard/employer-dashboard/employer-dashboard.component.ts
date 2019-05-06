import { Component, OnInit } from '@angular/core';
import {UserToApprove} from '../../user-approval/user-to-approve.model';
import {DaysOff} from '../../shared/days-off.model';
import {OffDayType } from '../../shared/off-day-type';

@Component({
  selector: 'app-employer-dashboard',
  templateUrl: './employer-dashboard.component.html',
  styleUrls: ['./employer-dashboard.component.sass']
})
export class EmployerDashboardComponent implements OnInit {

  usersToApprove: UserToApprove[] = [
    { date: new Date(), email: 'kek@kek.cz', name: 'Václav Jirák' },
    { date: new Date(), email: 'kuadas@kek.cz', name: 'Věnceslav Kárij' }
  ];

  daysOffToApprove: DaysOff[] = [
    { username: 'Václav Jirák', dateFrom: new Date(2019, 10, 13), dateTo: new Date(), type: OffDayType.Sickday },
    { username: 'Václav Jirák', dateFrom: new Date(2019, 10, 1), dateTo: new Date(), type: OffDayType.ExtraVacation },
  ];

  daysOff: DaysOff[] = [
    {
      username: '',
      dateFrom: new Date(2019, 5, 5),
      dateTo: new Date(2019, 5, 6),
      type: OffDayType.ExtraVacation
    },
    {
      username: '',
      dateFrom: new Date(2019, 5, 8),
      dateTo: new Date(2019, 5, 8),
      type: OffDayType.Sickday
    },
    {
      username: '',
      dateFrom: new Date(2019, 3, 8),
      dateTo: new Date(2019, 3, 9),
      type: OffDayType.Sickday
    },
  ];

  oncomingDaysOff: DaysOff[] = [];

  constructor() { }

  ngOnInit() {
    this.oncomingDaysOff = this.calculateComingDaysOff();
  }

  onDateSelect( date: Date ) {
    console.log('Date selected: ' + date.toDateString());
  }

  userApproved( user: UserToApprove, approved: boolean ) {
    console.log(user.name + ' - approved: ' + approved);
    this.usersToApprove.splice(
      this.usersToApprove.indexOf(user), 1
    );
  }

  daysOffApproved(daysOff: DaysOff, approved: boolean ) {
    console.log(daysOff.username + ', ' + approved);
    this.daysOffToApprove.splice(
      this.daysOffToApprove.indexOf(daysOff), 1
    );
  }

  daysOffRemoved(daysOff: DaysOff) {
    this.daysOff.splice(
      this.daysOff.indexOf(daysOff), 1
    );
    this.oncomingDaysOff.splice(
      this.oncomingDaysOff.indexOf(daysOff), 1
    );
  }

  private calculateComingDaysOff(): DaysOff[] {
    let oncomingDaysOff: DaysOff[] = [];

    const today = new Date();
    this.daysOff.forEach((dayOff) => {
      if (dayOff.dateTo >= today) {
        oncomingDaysOff.push(dayOff);
      }
    });

    return oncomingDaysOff;
  }
}
