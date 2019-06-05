import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProfileSettingsComponent } from './profile-settings.component';
import {
  MatButtonModule,
  MatCheckboxModule,
  MatDialogModule,
  MatDatepickerModule,
  MatFormFieldModule, MatInputModule
} from '@angular/material';
import {FormsModule} from '@angular/forms';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {SharedModule} from '../shared/shared.module';
import {TranslateModule} from '@ngx-translate/core';


@NgModule({
  declarations: [ProfileSettingsComponent],
  imports: [
    CommonModule,
    MatButtonModule,
    MatDialogModule,
    MatCheckboxModule,
    FormsModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatInputModule,
    NgbModule,
    SharedModule,
    TranslateModule
  ],
  entryComponents: [
    ProfileSettingsComponent
  ],
  exports: [
    ProfileSettingsComponent
  ]
})
export class ProfileSettingsModule { }
