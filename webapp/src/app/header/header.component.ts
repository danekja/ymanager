import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material';
import { ProfileSettingsComponent } from '../profile-settings/profile-settings.component';
import { ProfileService } from '../services/profile.service';
import { UserProfile } from '../models/user-profile.model';
import {LocalizationService} from "../localization/localization.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.sass']
})
export class HeaderComponent {
  @Input() name = 'John Doe';

  private notificationSettings: Date;

  constructor(
    private dialog: MatDialog,
    private profileService: ProfileService,
    private localizationService: LocalizationService
    ) {
    // profileService.getProfile()
    //   .subscribe((data: UserProfile) => this.notificationSettings = new Date(data.settings.notification));
  }

  onProfileClick(): void {
    this.dialog.open(ProfileSettingsComponent, {
      data: {
        shouldNotify: this.notificationSettings, // TODO potřeba?
        notifyDate: this.notificationSettings,
        notifyTime: {
          hour: this.notificationSettings.getHours(),
          minute: this.notificationSettings.getMinutes()
        }
      }
    });
  }
}
