import { Injectable } from '@angular/core';
import {UserProfile} from '../models/user-profile.model';
import {UserService} from './user.service';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {
  private profile: UserProfile;

  constructor(
    private userService: UserService
  ) {
    userService.getEmployeeProfile(1)
      .subscribe((data: UserProfile) => this.profile = data);
  }

  getProfile(): Observable<UserProfile> {
    return new Observable((observer) => {
      if (this.profile) {
        observer.next(this.profile);
        observer.complete();
      } else {
        this.userService.getEmployeeProfile(1) // TODO zmenit id na prihlaseneho uzivatele
          .subscribe((data: UserProfile) => {
            this.profile = data;
            observer.next(this.profile);
            observer.complete();
          });
      }
    });
  }

}
