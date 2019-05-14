import { NgModule } from '@angular/core';
import { DaysOffInfoComponent } from './days-off-info.component';
import {TranslateModule} from '@ngx-translate/core';

@NgModule({
  imports: [TranslateModule],
  declarations: [ DaysOffInfoComponent ],
  exports:      [ DaysOffInfoComponent ]
})
export class DaysOffInfoModule { }
