import { Injectable } from '@angular/core';
import { MenuItem } from './menu/menuItem';
import { Observable, of } from 'rxjs';
import { MENU_ITEMS } from './mock-menu-items';

@Injectable({
  providedIn: 'root'
})
export class MenuService {

  getMenuItems(): Observable<MenuItem[]> {
    return of(MENU_ITEMS);
  }

  constructor() { }
}
