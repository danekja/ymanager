import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-days-off-info',
  templateUrl: './days-off-info.component.html',
  styleUrls: ['./days-off-info.component.sass']
})
export class DaysOffInfoComponent {

  @Input() sickDaysRemaining: number;

  @Input() extraVacationRemaining: number;

  constructor() { }
}
