import {Component, OnInit} from '@angular/core';
import {UserService} from '../services/api/user.service';
import {VacationType} from '../enums/common.enum';

@Component({
  selector: 'app-coming-days-off',
  templateUrl: './oncoming-days-off.component.html',
  styleUrls: ['./oncoming-days-off.component.sass']
})
export class OncomingDaysOffComponent implements OnInit {

  constructor(
    private userService: UserService
  ) { }

  ngOnInit() {
    // TODO api call na získání nadcházejících voln
  }

  private daysOffRemoved( daysOffId: number ) {
    // TODO api call na odstranění nadcházejícího volna
  }

  // TODO možná zbytečný - bude api call na získání nadcházejících voln
  // private calculateComingDaysOff(): DaysOff[] {
  //   let oncomingDaysOff: DaysOff[] = [];
  //
  //   const today = new Date();
  //   this.daysOff.forEach((dayOff) => {
  //     if (dayOff.dateTo >= today) {
  //       oncomingDaysOff.push(dayOff);
  //     }
  //   });
  //
  //   return oncomingDaysOff;
  // }

  // TODO
  //  days-off-approval duplicate
  private daysOffTypeToString(vacationType: VacationType): string {
    switch (vacationType) {
      case VacationType.VACATION:
        return 'Extra dovolená';
      case VacationType.SICKDAY:
        return 'Sickdays';
    }
  }
}
