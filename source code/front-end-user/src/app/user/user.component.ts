import {Component, OnInit} from '@angular/core';
import {TokenStorageService} from '../_services/token-storage.service';
import {UserService} from '../_services/user.service';
import {UserProfile} from '../models/user-profile';
import {Router} from '@angular/router';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent {
 toggledMenu = false;
toggleMenu() {
    this.toggledMenu = !this.toggledMenu;
  }
}