import {AfterContentInit, AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {UserService} from '../../_services/user.service';
import {UserAccount} from '../../models/user-account';
import {PaginationDetail} from '../../models/pagination/pagination-detail';
import {delay, switchMap} from 'rxjs/operators';
import {ToastrService} from 'ngx-toastr';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { UserRole } from 'src/app/models/user-role.enum';

@Component({
  selector: 'app-manage-user',
  templateUrl: './manage-user.component.html',
  styleUrls: ['./manage-user.component.scss']
})
export class ManageUserComponent implements OnInit, AfterContentInit {
  roleCEO: boolean = false;
  rolePostOfficeManager: boolean = false;
  roleDepotManager: boolean = false;
  userList: UserAccount[] = [];
  paginationDetail: PaginationDetail;
  userRoles: string[] = [];
  skeleton = true;
  pageOptions: any = [
    {display: 20, num: 20},
    {display: 50, num: 50},
    {display: 100, num: 100},
    {display: 'Tất cả', num: ''},
  ];
  searchKeyWord = '';
  pageCountShowing = 20;

  constructor(private userService: UserService, private toast: ToastrService, private tokenStorageService: TokenStorageService) {
  }


  ngOnInit(): void {
    this.userRoles = this.tokenStorageService.getUser().roles;
    this.fetchUserList();

  }

  fetchUserList() {
    if (this.userRoles.includes(UserRole.ROLE_CEO.toString())) {
      this.roleCEO = true;
      this.userService.getUserList(0, 20).subscribe(res => {
        this.userList = res.data;
        this.userList = this.userList.filter(user => user.roles[0].name !== "ROLE_CEO");
        this.paginationDetail = res.paginationDetails;
        this.skeleton = false;
      });
    } else if (this.userRoles.includes(UserRole.ROLE_POST_OFFICE_MANAGER.toString())){
      this.rolePostOfficeManager = true;
      this.userService.getUserListByPost(0, 20).subscribe(res => {
        this.userList = res.data;
        this.paginationDetail = res.paginationDetails;
        this.skeleton = false;
      });
    } else if (this.userRoles.includes(UserRole.ROLE_DEPOT_MANAGER.toString())) {
      this.roleDepotManager = true;
      this.userService.getUserListByDepot(0, 20).subscribe(res => {
        this.userList = res.data;
        this.paginationDetail = res.paginationDetails;
        this.skeleton = false;
      });
    }


  }

  trackById(index: number, item) {
    return item.id;
  }

  exportUserToExcel() {
    this.userService.exportExcel(false).subscribe(response => {
      const link = document.createElement('a');
      link.href = window.URL.createObjectURL(new Blob([response], {type: 'text/csv'}));
      link.download = 'users.csv';
      link.click();
    });
  }

  goPreviousPage() {
    const isFirstPage: boolean = this.paginationDetail.isFirstPage;
    if (!isFirstPage) {
      this.userService.searchUserList(this.paginationDetail.previousPage.pageNumber, this.pageCountShowing, this.searchKeyWord)
        .subscribe(res => {
          this.userList = res.data;
          this.paginationDetail = res.paginationDetails;
          console.table(this.userList);
        });
    }

  }

  goNextPage() {
    const isLastPage = !this.paginationDetail.nextPage.available;
    if (!isLastPage) {
      this.userService.searchUserList(this.paginationDetail.nextPage.pageNumber, this.pageCountShowing, this.searchKeyWord
      ).subscribe(res => {
        this.userList = res.data;
        this.paginationDetail = res.paginationDetails;
        console.table(this.paginationDetail);
      });
    }
  }

  fetchUsersAfterCRUD($event: any) {
    this.userList = $event.data;
    this.paginationDetail = $event.paginationDetails;
  }

  changePageShow(value: any) {
    this.pageCountShowing = value;
    if (!this.pageCountShowing) {
      this.userService.getUserList(0, this.paginationDetail.totalCount).subscribe(res => {
        this.userList = res.data;
        this.paginationDetail = res.paginationDetails;
      });
    } else {
      this.userService.getUserList(0, this.pageCountShowing).subscribe(res => {
        this.userList = res.data;
        this.paginationDetail = res.paginationDetails;
      });
    }
  }

  searchUser(searchKeyWord: string) {
    console.log(searchKeyWord);
    this.userService.searchUserList(0, 20, searchKeyWord).subscribe(res => {
      this.userList = res.data;
      this.paginationDetail = res.paginationDetails;
    });
  }

  ngAfterContentInit(): void {
    console.log('after view init');
  }

  changeDeleted(user: UserAccount) {
    user.deleted = !user.deleted;
    this.userService.deleteUser(user.id, user.deleted)
      .pipe(switchMap(res => this.userService.getUserList(0, 20)))
      .subscribe(res => {
        this.toast.success('Đã thay đổi trạng thái tài khoản', 'Thành công');
      }, error => {
        user.deleted = !user.deleted;
        this.toast.error('Không thể thay đổi trạng thái', 'Lỗi');
      });
  }
}
