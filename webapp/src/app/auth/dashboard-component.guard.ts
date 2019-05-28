import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {ProfileService} from '../services/util/profile.service';
import {BaseGuard} from './base-guard';

@Injectable({
  providedIn: 'root'
})
export class DashboardComponentGuard extends BaseGuard implements CanActivate {

  constructor(private profileService: ProfileService, protected router: Router) {
    super(router);
  }

  /**
   * All logged users can navigate to dashboard component
   * other users are navigate to login page
   * @param route activated route snapshot
   * @param state router state snapshot
   */
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | boolean {
    return new Observable<boolean>((obs) => {
      this.profileService.getLoggedUser()
        .subscribe(() => {
            obs.next(true);
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
