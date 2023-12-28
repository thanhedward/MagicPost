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
  roleCEO: boolean = false;
  rolePostOfficeManager: boolean = false;
  rolePostOfficeEmployee: boolean = false;
  roleDepotManager: boolean = false;
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
    } else if (this.userRoles.includes(UserRole.ROLE_DEPOT_MANAGER.toString())) {
      this.roleDepotManager = true
    } else if (this.userRoles.includes(UserRole.ROLE_DEPOT_EMPLOYEE.toString())) {
      this.roleDepotEmployee = true
    } else if (this.userRoles.includes(UserRole.ROLE_POST_OFFICE_EMPLOYEE.toString())) {
      this.rolePostOfficeEmployee = true
    } 
    
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
