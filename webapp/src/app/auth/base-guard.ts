import {Router} from '@angular/router';

/**
 * Base class for all guards, provides navigating users to
 * components
 */
export class BaseGuard {
  constructor(protected router: Router) {

  }

  protected navigateUserToLogin(): boolean {
    console.log('Navigating user to login');
    this.router.navigate(['/login']);

    return false;
  }

  protected navigateUserToDashboard(): boolean {
    console.log('Navigating user to dashboard');
    this.router.navigate(['/dashboard']);

    return false;
  }

  protected navigateUserTo(component: string): boolean {
    this.router.navigate([component]);

    return false;
  }
}
