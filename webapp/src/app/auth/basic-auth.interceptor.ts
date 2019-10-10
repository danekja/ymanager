import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';

import {ProfileService} from '../services/util/profile.service';

/**
 * Adds authentication token to each request.
 */
@Injectable()
export class BasicAuthInterceptor implements HttpInterceptor {
  constructor(private profileService: ProfileService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // add authorization header with basic auth credentials if available
    const currentUser = window.btoa(this.profileService.currentUserValue + ":");
    if (currentUser) {
      request = request.clone({
        withCredentials: true,
        setHeaders: {
          authorization: `Basic ${currentUser}`
        }
      });
    }

    return next.handle(request);
  }
}
