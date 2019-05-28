import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {ProfileService} from '../services/util/profile.service';
import {UserProfile} from '../models/user.model';
import {UserType} from '../enums/common.enum';
import {Observable} from 'rxjs';
import {BaseGuard} from './base-guard';

@Injectable({
  providedIn: 'root'
})
export class EmployeeComponentGuard extends BaseGuard implements CanActivate {

  constructor(private profileService: ProfileService, protected router: Router) {
    super(router);
  }

  /**
   * Logged user with access rights as employer can navigate to employee component
   * Logged user with access rights as employee is navigated to dashboard
   * User that is not logged is navigated to login page
   * @param route activated route snapshot
   * @param state router state snapshot
   */
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | boolean {
    return new Observable<boolean>((obs) => {
      this.profileService.getLoggedUser()
        .subscribe((userProfile: UserProfile) => {
            if (userProfile.role === UserType.EMPLOYER) {
              console.log('User can navigate to the page');
              obs.next(true);
            }

            obs.next(this.navigateUserToDashboard());
            obs.complete();
          },
          () => {
            obs.next(this.navigateUserToLogin());
            obs.complete();
          }
        );
    });
  }

}
