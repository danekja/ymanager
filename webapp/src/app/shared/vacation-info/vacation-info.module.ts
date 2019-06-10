import { NgModule } from '@angular/core';
import { VacationInfoComponent } from './vacation-info.component';
import {TranslateModule} from '@ngx-translate/core';

@NgModule({
  imports: [TranslateModule],
  declarations: [ VacationInfoComponent ],
  exports:      [ VacationInfoComponent ]
})
export class VacationInfoModule { }
