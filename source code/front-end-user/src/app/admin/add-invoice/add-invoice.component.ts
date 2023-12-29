import {Component, OnInit} from '@angular/core';
import { FormBuilder, FormGroup, FormArray } from '@angular/forms';
import {ToastrService} from 'ngx-toastr';
import { ParcelService } from 'src/app/_services/parcel.service';
import { InvoiceService } from 'src/app/_services/invoice.service';
import { sendParcel } from 'src/app/models/send-parcel';
import { postDepotInvoice } from 'src/app/models/post-depot-invoice';
import {UserRole} from '../../models/user-role.enum';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { AddressService } from 'src/app/_services/address.service';
@Component({
  selector: 'add-invoice',
  templateUrl: './add-invoice.component.html',
  styleUrls: ['./add-invoice.component.scss']
})
export class AddInvoiceComponent implements OnInit {

  rfAdd: FormGroup;
  sendParcel: sendParcel;
  sendParcelList: sendParcel[] = [];
  postDepotInvoice: postDepotInvoice;
  tagList: any[] = [];
  rolePostOfficeEmployee: boolean = false;
  roleDepotEmployee: boolean = false;
  userRoles: string[] = [];
  options: any[] = [];
  selectedOption: string;
  constructor(
    private fb: FormBuilder,
    private invoiceService: InvoiceService,
    private toast: ToastrService,
    private parcelService: ParcelService,
    private tokenStorageService: TokenStorageService,
    private addressService: AddressService) {
  }
  ngOnInit(): void {
    this.userRoles = this.tokenStorageService.getUser().roles;
    if (this.userRoles.includes(UserRole.ROLE_POST_OFFICE_EMPLOYEE.toString())) {
      this.rolePostOfficeEmployee = true;
      this.parcelService.getParcelList().subscribe(res => {
        this.tagList = res;
        console.log(res)
      });
    } else if (this.userRoles.includes(UserRole.ROLE_DEPOT_EMPLOYEE.toString())) {
      this.roleDepotEmployee = true;
      this.getDepotHasParcel();
      this.parcelService.getParceltoPostList(this.selectedOption).subscribe(res => {
        this.tagList = res;
        console.log(res)
      });
    }
    this.rfAdd = this.fb.group({
    });
    this.selectedOption = this.options[0].name
    
  }
  
  onCheckboxChange(tagId: number, isChecked: boolean) {
    if (isChecked) {
      this.sendParcelList.push(new sendParcel(tagId));
    } else {
      const index = this.sendParcelList.findIndex((parcel) => parcel.id === tagId);
      if (index !== -1) {
        this.sendParcelList.splice(index, 1);
      }
    }
  }

  onSubmit() {
    this.postDepotInvoice = new postDepotInvoice(this.sendParcelList);
    if(this.rolePostOfficeEmployee){
      this.invoiceService.addInvoice(this.postDepotInvoice).subscribe(
        (res) => {
          this.getParcelList();
          this.toast.success('Đã thêm đơn hàng cho khách', 'Thành công');
        },
        (error) => {
          console.log(this.sendParcelList)
          this.toast.error('Đã xảy ra lỗi khi thêm đơn hàng', 'Lỗi');
        }
      );
      
    } else {
      this.invoiceService.addInvoiceDepotToDepot(this.postDepotInvoice).subscribe(
        (res) => {
          this.getParcelList();
          this.toast.success('Đã thêm đơn hàng cho khách', 'Thành công');
        },
        (error) => {
          console.log(this.sendParcelList)
          this.toast.error('Đã xảy ra lỗi khi thêm đơn hàng', 'Lỗi');
        }
      );
    }

    
  }
  
  getParcelList() {
    if(this.rolePostOfficeEmployee){
      this.parcelService.getParcelList().subscribe(res => {
        this.tagList = res;
        console.log(res)
      });
    } else if(this.roleDepotEmployee){
      this.parcelService.getParceltoPostList(this.selectedOption).subscribe(res => {
        this.tagList = res;
        console.log(res)
      });
    }
    
  }
  getDepotHasParcel(){
    this.addressService.getProvinceHasParcel().subscribe(res => {
      this.options = res;
      console.log(res)
    });
  }
}

  

  



