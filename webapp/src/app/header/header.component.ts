import {Component} from '@angular/core';
import {MatDialog} from '@angular/material';
import {LocalizationService} from '../localization/localization.service';
import {UserService} from '../services/api/user.service';
import {UserProfile} from '../models/user.model';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.sass']
})
export class HeaderComponent {
  profile: UserProfile;

  constructor(
    private dialog: MatDialog,
    private localizationService: LocalizationService,
    private userService: UserService
    ) {
    userService.getLoggedUserProfile()
      .subscribe((data: UserProfile) => this.profile = data);
  }

  onProfileClick(): void {
    // TODO (až bude hotovej endpoint na post notifikace)
    // this.dialog.open(ProfileSettingsComponent, {
    //   data: {
    //     notifyDate: this.notificationSettings,
    //     notifyTime: {
    //       hour: this.notificationSettings.getHours(),
    //       minute: this.notificationSettings.getMinutes()
    //     }
    //   }
    // });
  }
}
