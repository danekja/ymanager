import {Component, OnInit} from '@angular/core';
import {MenuItem} from '../models/menu-item.model';
import {Location, LocationStrategy, PathLocationStrategy} from '@angular/common';
import {MenuService} from '../services/util/menu.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.sass'],
  providers: [Location, {provide: LocationStrategy, useClass: PathLocationStrategy}]
})
export class MenuComponent implements OnInit {
  _menuItems: MenuItem[];
  private _selectedMenuItem: MenuItem;

  constructor(private location: Location, private menuService: MenuService) {
  }

  ngOnInit() {
    this.menuService.getMenuItems()
      .subscribe((data: MenuItem[]) => {
        this._menuItems = data;

        this._selectedMenuItem = this._menuItems[0];
        const path = this.location.path().split('/');
        const endOfPath = path[path.length - 1];

        for (const item of this._menuItems) {
          if (item.routePath === endOfPath) {
            this._selectedMenuItem = item;
          }
        }
      });
  }

  onSelect(menuItem: MenuItem): void {
    this._selectedMenuItem = menuItem;
  }
}
