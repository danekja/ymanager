import {Injectable} from '@angular/core';
import {UserService} from '../api/user.service';
import {UserProfile} from '../../models/user.model';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {
  private profile: UserProfile;

  constructor(private userService: UserService) {

  }

  /**
   * Returns logged user profile if the server responds
   * with valid profile otherwise observer returns error
   * with message 'Cannot log in'
   *
   * The idea was to cache the logged user profile but
   * the changes to logged user would not be seen until
   * the user logged off and then logged back in
   */
  public getLoggedUser() {
    return new Observable<UserProfile>((obs) => {
      this.userService.getLoggedUserProfile()
        .subscribe((userProfile: UserProfile) => {
            this.profile = {...userProfile};
            obs.next(this.profile);
            obs.complete();
          },
          error1 => {
            obs.error(error1);
            obs.complete();
          });
    });
  }

  /**
   * Do not use at the start of the application
   * User might be logged but the service hasn't
   * finished the request for the user profile
   */
  public isUserLogged(): boolean {
    return this.profile == null;
  }

  public logUserOff(): void {
    this.profile = null;
  }
}
