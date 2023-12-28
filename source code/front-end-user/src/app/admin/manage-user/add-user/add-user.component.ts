import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../../_services/user.service';
import {UserAccount} from '../../../models/user-account';
import {UserProfile} from '../../../models/user-profile';
import {PageResult} from '../../../models/page-result';
import {ToastrService} from 'ngx-toastr';
import {Intake} from '../../../models/intake';
import { AddressService } from 'src/app/_services/address.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { UserRole } from 'src/app/models/user-role.enum';
@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss']
})
export class AddUserComponent implements OnInit {
  roleCEO: boolean = false;
  rolePostOfficeManager: boolean = false;
  roleDepotManager: boolean = false;
  showModalAdd = false;
  rfAddDepotManager: FormGroup;
  rfAddPostManager: FormGroup;
  @Output() usersAddOutput = new EventEmitter<PageResult<UserAccount>>();
  @Input() active = false;
  @Input() tabTitle: string;
  // @ViewChild(FileUploadComponent) fileUpload: FileUploadComponent;
  openTab = 1;
  pageResult: PageResult<UserAccount>;
  userExcelFile: any;
  intakes: Intake[] = [];
  userImportSuccess: UserAccount[] = [];
  userTotal: number;
  depotProvinceList: any[] = [];
  districtList: any[] = [];
  selectedProvince: string;
  userRoles: string[] = [];

  constructor(private userService: UserService,
              private fb: FormBuilder,
              private toast: ToastrService,
              private addressService: AddressService,
              private tokenStorageService: TokenStorageService
              ) {
  }
  
  get username() {
    if(this.openTab == 1){
      return this.rfAddDepotManager.get('username');
    }
    else {
      return this.rfAddPostManager.get('username');
    }
  }

  get firstName() {
    if(this.openTab == 1){
      return this.rfAddDepotManager.get('firstName');
    }
    else {
      return this.rfAddPostManager.get('firstName');
    }
  }

  get lastName() {
    if(this.openTab == 1){
      return this.rfAddDepotManager.get('lastName');
    }
    else {
      return this.rfAddPostManager.get('lastName');
    }
  
  }

  get email() {
    if(this.openTab == 1){
      return this.rfAddDepotManager.get('email');
    }
    else {
      return this.rfAddPostManager.get('email');
    }
  }

  get province(){
    return this.rfAddDepotManager.get('province');
   
  }
  get provincePost(){
    return this.rfAddPostManager.get('provincePost');
  }
  get districtPost(){
    return this.rfAddPostManager.get('districtPost');
  }

  ngOnInit(): void {
    this.userRoles = this.tokenStorageService.getUser().roles;
    if (this.userRoles.includes(UserRole.ROLE_CEO.toString())) {
      this.roleCEO = true;
    } else if (this.userRoles.includes(UserRole.ROLE_POST_OFFICE_MANAGER.toString())) {
      this.rolePostOfficeManager = true;
    } else if (this.userRoles.includes(UserRole.ROLE_DEPOT_MANAGER)) {
      this.roleDepotManager = true
    }
    //  else {
    //   this.tokenStorageService.signOut()
    // }
    this.getDepotProvince();
    this.rfAddDepotManager?.reset(this.rfAddDepotManager.value);
    this.rfAddPostManager?.reset(this.rfAddPostManager.value);
    if(this.roleCEO){
      this.rfAddDepotManager = this.fb.group({
        username: ['', {
          validators: [Validators.required, Validators.minLength(6)],
          asyncValidators: [this.userService.validateUsername()],
          updateOn: 'blur'
        }],
        email: ['', {
          validators: [Validators.required, Validators.email, Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')],
          asyncValidators: [this.userService.validateEmail()],
          updateOn: 'blur'
        }],
        firstName: ['', Validators.required],
        lastName: ['', Validators.required],
        province: ['', Validators.required]
        
      });
    } else{
      this.rfAddDepotManager = this.fb.group({
        username: ['', {
          validators: [Validators.required, Validators.minLength(6)],
          asyncValidators: [this.userService.validateUsername()],
          updateOn: 'blur'
        }],
        email: ['', {
          validators: [Validators.required, Validators.email, Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')],
          asyncValidators: [this.userService.validateEmail()],
          updateOn: 'blur'
        }],
        firstName: ['', Validators.required],
        lastName: ['', Validators.required],
        
      });
    }
    
    this.rfAddPostManager = this.fb.group({
      username: ['', {
        validators: [Validators.required, Validators.minLength(6)],
        asyncValidators: [this.userService.validateUsername()],
        updateOn: 'blur'
      }],
      email: ['', {
        validators: [Validators.required, Validators.email, Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')],
        asyncValidators: [this.userService.validateEmail()],
        updateOn: 'blur'
      }],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      provincePost: ['', Validators.required],
      districtPost: ['', Validators.required],
    });
    

  }

  toggleModalAdd() {
    this.showModalAdd = !this.showModalAdd;
  }

  closeModal() {
    this.showModalAdd = false;
    this.rfAddDepotManager.reset();
    this.userTotal = 0;
  }

  onSubmitDepotManager() {
    const profile = new UserProfile(this.firstName.value, this.lastName.value);
    const user: UserAccount = new UserAccount(this.username.value, this.email.value, profile);
    if(this.roleCEO) {
      this.userService.addDepotManager(user,this.province.value)
      // .pipe(switchMap(res => this.userService.getUserList(0, 20)))
      .subscribe(res => {
        this.closeModal();
        this.showSuccess();
        // this.pageResult = res;
        this.usersAddOutput.emit(this.pageResult);
      });
    } else if(this.roleDepotManager){
      this.userService.addDepotEmployee(user)
      // .pipe(switchMap(res => this.userService.getUserList(0, 20)))
      .subscribe(res => {
        this.closeModal();
        this.showSuccess();
        // this.pageResult = res;
        this.usersAddOutput.emit(this.pageResult);
      });
    } else if(this.rolePostOfficeManager){
      this.userService.addPostOfficeEmployee(user)
      // .pipe(switchMap(res => this.userService.getUserList(0, 20)))
      .subscribe(res => {
        this.closeModal();
        this.showSuccess();
        // this.pageResult = res;
        this.usersAddOutput.emit(this.pageResult);
      });
    }
    
    
  }

  onPostSubmit(){
    const profile = new UserProfile(this.firstName.value, this.lastName.value);
    const user: UserAccount = new UserAccount(this.username.value, this.email.value, profile);
    this.userService.addPostOfficeManager(user, this.provincePost.value, this.districtPost.value)
      // .pipe(switchMap(res => this.userService.getUserList(0, 20)))
      .subscribe(res => {
        this.closeModal();
        this.showSuccess();
        // this.pageResult = res;
        this.usersAddOutput.emit(this.pageResult);
      });
  }
  
  showSuccess() {
    if(this.openTab == 2){
      this.toast.success('Đã thêm thành công trưởng điểm giao dịch!', 'Thành công',);
    } else {
      if(this.roleCEO){
        this.toast.success('Đã thêm thành công trưởng điểm tập kết!', 'Thành công',);
      } else if(this.roleDepotManager) {
        this.toast.success('Đã thêm thành công nhân viên điểm tập kết!', 'Thành công',);
      } else if(this.rolePostOfficeManager) {
        this.toast.success('Đã thêm thành công nhân viên điểm giao dịch!', 'Thành công',);
      }
      
    }
    
  }
  onChange(){
    console.log(this.province.value)
    console.log(this.openTab)
  }

  onProvinceChange() {
    if (this.provincePost.value) {
      this.addressService.getPostDistricts(this.provincePost.value).subscribe(res => {
        this.districtList = res;
      });
    } else {
      this.districtList = [];
    }
  }

  toggleTabs($tabNumber: number) {
    this.openTab = $tabNumber;
  }
  getDepotProvince() {
    this.addressService.getDepot().subscribe(res => {
      this.depotProvinceList = res;
      console.log(res)
    });
  }
}
