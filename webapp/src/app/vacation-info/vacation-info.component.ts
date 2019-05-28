import { Component, Input } from '@angular/core';
import {LocalizationService} from '../localization/localization.service';

@Component({
  selector: 'app-vacation-info',
  templateUrl: './vacation-info.component.html',
  styleUrls: ['./vacation-info.component.sass']
})
export class VacationInfoComponent {

  @Input() sickDaysRemaining: number;
  @Input() extraVacationRemaining: number;

  constructor(private localizationService: LocalizationService) { }
}
