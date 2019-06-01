import {Injectable} from '@angular/core';
import {UserService} from '../api/user.service';
import {UserProfile} from '../../models/user.model';
import {MenuItem} from '../../models/menu-item.model';
import {UserType} from '../../enums/common.enum';
import {Observable, of} from 'rxjs';
import {ProfileService} from './profile.service';

@Injectable({
  providedIn: 'root'
})
export class MenuService {

  constructor(private profileService: ProfileService) {
  }

  getMenuItems(cached?: boolean) {
    if (cached) {
      this.profileService.getLoggedUser(true)
        .subscribe((profile: UserProfile) => {
          return of(this.createAppropriateMenuForUser(profile));
        });
    }

    return new Observable((observer) => {
      this.profileService.getLoggedUser()
        .subscribe((profile: UserProfile) => {
            observer.next(this.createAppropriateMenuForUser(profile));
            observer.complete();
          },
          () => {
            observer.next([]);
            observer.complete();
          });
    });
  }

  private createAppropriateMenuForUser(profile: UserProfile): MenuItem[] {
    const menuItems: MenuItem[] = [];
    menuItems.push({name: 'Dashboard', routePath: 'dashboard'});
    if (profile.role === UserType.EMPLOYER) {
      menuItems.push({name: 'Zaměstnanci', routePath: 'employees'});
    }

    return menuItems;
  }
}
