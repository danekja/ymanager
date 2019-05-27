import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

const DEFAULT_MINUTE_STEP = 15;

@Component({
  selector: 'app-datetime',
  templateUrl: './datetime.component.html',
  styleUrls: ['./datetime.component.sass']
})
export class DatetimeComponent implements OnInit {

  @Input() date: Date;
  @Output() dateChange = new EventEmitter<Date>();

  @Input() time: string; // HH:mm format
  @Output() timeChange = new EventEmitter<string>();

  @Input() minuteStep = DEFAULT_MINUTE_STEP;

  constructor() { }

  ngOnInit() {
  }

  private dateChanged(): void {
    this.dateChange.emit(this.date);
  }

  private timeChanged(newTime: string): void {
    this.time = newTime;
    this.timeChange.emit(this.time);
  }
}
