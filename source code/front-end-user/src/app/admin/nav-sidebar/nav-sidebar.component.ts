import {Component, OnInit} from '@angular/core';
import {TokenStorageService} from '../../_services/token-storage.service';
import {UserRole} from '../../models/user-role.enum';

@Component({
  selector: 'app-nav-sidebar',
  templateUrl: './nav-sidebar.component.html',
  styleUrls: ['./nav-sidebar.component.scss']
})
export class NavSidebarComponent implements OnInit {

  collapseShow = 'hidden';
  userCollapseShow = false;
  questionBankCollapseShow = false;
  userRoles: string[] = [];
  // roleAdmin: boolean;
  // roleLecturer: boolean;
  roleCEO: boolean;
  rolePostOfficeManager: boolean;
  rolePostOfficeEmployee: boolean = false;
  roleDepotManager: boolean;
  roleDepotEmployee: boolean = false;
  roleUser: boolean;

  constructor(private tokenStorageService: TokenStorageService) {
  }

  ngOnInit() {
    this.userRoles = this.tokenStorageService.getUser().roles;
    if (this.userRoles.includes(UserRole.ROLE_CEO.toString())) {
      this.roleCEO = true;
    } else if (this.userRoles.includes(UserRole.ROLE_POST_OFFICE_MANAGER.toString())) {
      this.rolePostOfficeManager = true;
    } else if (this.userRoles.includes(UserRole.ROLE_DEPOT_MANAGER)) {
      this.roleDepotManager = true
    } 
    // else {
    //   this.tokenStorageService.signOut()
    // }
    
  }

  toggleCollapseShow(classes) {
    this.collapseShow = classes;
  }

  userCollapse() {
    this.userCollapseShow = !this.userCollapseShow;
  }

  questionBankCollapse() {
    this.questionBankCollapseShow = !this.questionBankCollapseShow;
  }
}
