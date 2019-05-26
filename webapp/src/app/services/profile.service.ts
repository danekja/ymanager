import {Injectable} from '@angular/core';
import {UserService} from './user.service';
import {Observable} from 'rxjs';
import {UserProfile} from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {
  private profile: UserProfile;

  constructor(
    private userService: UserService
  ) {
    // userService.getUserProfile(1)
    //   .subscribe((data: UserProfile) => this.profile = data);
  }

  getProfile(): Observable<UserProfile> {
    return new Observable((observer) => {
      if (this.profile) {
        observer.next(this.profile);
        observer.complete();
      } else {
        this.userService.getUserProfile(1) // TODO zmenit id na prihlaseneho uzivatele
          .subscribe((data: UserProfile) => {
            this.profile = data;
            observer.next(this.profile);
            observer.complete();
          });
      }
    });
  }

}
