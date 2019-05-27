import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {UserProfile} from '../../models/user.model';
import {Calendar} from '../../models/calendar.model';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile-dialog.component.html',
  styleUrls: ['./user-profile-dialog.component.sass']
})
export class UserProfileDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<UserProfileDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: UserProfileDialogData
  ) { }

  ngOnInit() {
  }

  onCloseClick(): void {
    this.dialogRef.close({
      isConfirmed: false
    });
  }
}

export class UserProfileDialogData {
  profile: UserProfile;
  calendar: Calendar[];
}
