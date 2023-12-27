import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../../_services/user.service';
import {UserAccount} from '../../../models/user-account';
import {UserProfile} from '../../../models/user-profile';
import {PageResult} from '../../../models/page-result';
import {ToastrService} from 'ngx-toastr';
import {Intake} from '../../../models/intake';
import { AddressService } from 'src/app/_services/address.service';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss']
})
export class AddUserComponent implements OnInit {
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

  constructor(private userService: UserService,
              private fb: FormBuilder,
              private toast: ToastrService,
              private addressService: AddressService,
              
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
    if(this.openTab == 1){
      return this.rfAddDepotManager.get('province');
    }
    else {
      return this.rfAddPostManager.get('province');
    }
   
  }

  ngOnInit(): void {
    this.getDepotProvince();
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
      province: ['', Validators.required],
    });
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
    this.userService.addDepotManager(user,this.province.value)
      // .pipe(switchMap(res => this.userService.getUserList(0, 20)))
      .subscribe(res => {
        this.closeModal();
        this.showSuccess();
        // this.pageResult = res;
        this.usersAddOutput.emit(this.pageResult);
      });
    
  }

  onPostSubmit(){
    const profile = new UserProfile(this.firstName.value, this.lastName.value);
    const user: UserAccount = new UserAccount(this.username.value, this.email.value, profile);
    this.userService.addDepotManager(user,this.province.value)
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
      this.toast.success('Đã thêm thành công trưởng điểm tập kết!', 'Thành công',);
    }
    
  }
  onChange(){
    console.log(this.province.value)
    console.log(this.openTab)

    
  }

  onProvinceChange() {
    if (this.selectedProvince) {
      this.addressService.getDistricts(this.selectedProvince).subscribe(res => {
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
