import { Component, Input } from '@angular/core';
import {TimeUnit} from "../enums/common.enum";

@Component({
  selector: 'app-days-off-info',
  templateUrl: './days-off-info.component.html',
  styleUrls: ['./days-off-info.component.sass']
})
export class DaysOffInfoComponent {

  @Input() sickDaysRemaining: {
    value: number;
    unit: TimeUnit;
  };

  @Input() extraVacationRemaining: {
    value: number;
    unit: TimeUnit;
  };

  constructor() { }
}