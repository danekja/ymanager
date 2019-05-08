import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-profile-settings',
  templateUrl: './profile-settings.component.html',
  styleUrls: ['./profile-settings.component.sass']
})
export class ProfileSettingsComponent {
  constructor(
    public dialogRef: MatDialogRef<ProfileSettingsComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ProfileSettingsDialogData,
  ) {
    if (data.notifyTime == null) {
      data.notifyTime = { hour: 8, minute: 0 };
    }
  }

  onConfirmClick(): void {
    // TODO API CALL
    this.dialogRef.close();
  }

  onCloseClick(): void {
    this.dialogRef.close();
  }
}

export interface ProfileSettingsDialogData {
  shouldNotify: boolean;
  notifyDate: FormControl;
  notifyTime: { hour: number, minute: number };
}
