import { Component, OnInit } from '@angular/core';
import { MenuItem } from './menu-item';

const MENU_ITEMS: MenuItem[] = [
  {name: 'Dashboard', routePath: 'dashboard'},
  {name: 'Zaměstnanci', routePath: 'employees'},
];

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.sass']
})
export class MenuComponent implements OnInit {
  _menuItems: MenuItem[];
  private _selectedMenuItem: MenuItem;

  constructor() {
    this._menuItems = MENU_ITEMS;
  }

  ngOnInit() {
  }

  onSelect(menuItem: MenuItem): void {
    this._selectedMenuItem = menuItem;
  }
}
