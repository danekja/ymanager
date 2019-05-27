import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Calendar} from '../models/calendar.model';

@Component({
  selector: 'app-coming-days-off',
  templateUrl: './oncoming-vacation.component.html',
  styleUrls: ['./oncoming-vacation.component.sass']
})
export class OncomingVacationComponent implements OnInit {

  @Input() oncomingVacation: Calendar[];

  @Output() vacationRemove = new EventEmitter<Calendar>();

  @Output() vacationEdit = new EventEmitter<Calendar>();

  constructor() { }

  ngOnInit() { }
}
