import { Component, OnInit } from '@angular/core';
import { MenuService } from '../services/menu.service';
import { MenuItem } from './menu-item';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.sass']
})
export class MenuComponent implements OnInit {
  menuItems: MenuItem[];
  selectedMenuItem: MenuItem;


  getMenuItems(): void {
    this.menuService.getMenuItems()
      .subscribe(menuItems => this.menuItems = menuItems);
  }

  onSelect(menuItem: MenuItem): void {
    this.selectedMenuItem = menuItem;
  }

  constructor(private menuService: MenuService) { }

  ngOnInit() {
    this.getMenuItems();
  }


}
