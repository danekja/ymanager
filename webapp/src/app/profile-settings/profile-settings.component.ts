import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

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
  }

  onConfirmClick(): void {
    this.dialogRef.close({
      isConfirmed: true,
      notifyDate: this.data.notifyDate,
      notifyTime: this.data.notifyTime
    });
  }

  onCloseClick(): void {
    this.dialogRef.close({
      isConfirmed: false
    });
  }
}

export interface ProfileSettingsDialogData {
  notifyDate: Date;
  notifyTime: string;
}
