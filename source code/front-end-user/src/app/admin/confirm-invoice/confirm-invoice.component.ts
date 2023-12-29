import {Component, OnInit} from '@angular/core';
import { FormBuilder, FormGroup, FormArray } from '@angular/forms';
import {ToastrService} from 'ngx-toastr';
import { ParcelService } from 'src/app/_services/parcel.service';
import { InvoiceService } from 'src/app/_services/invoice.service';
import { sendParcel } from 'src/app/models/send-parcel';
import { postDepotInvoice } from 'src/app/models/post-depot-invoice';
import {UserRole} from '../../models/user-role.enum';
import { ConfirmService } from 'src/app/_services/confirm-invoice.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
@Component({
  selector: 'confirm-invoice',
  templateUrl: './confirm-invoice.component.html',
  styleUrls: ['./confirm-invoice.component.scss']
})
export class ConfirmInvoiceComponent implements OnInit {

  sendParcel: sendParcel;
  sendParcelList: sendParcel[] = [];
  postDepotInvoice: postDepotInvoice;
  tagList: any[] = [];
  rolePostOfficeEmployee: boolean = false;
  roleDepotEmployee: boolean = false;
  userRoles: string[] = [];
  constructor(
    private fb: FormBuilder,
    private invoiceService: InvoiceService,
    private toast: ToastrService,
    private parcelService: ParcelService,
    private confirmService: ConfirmService,
    private tokenStorageService: TokenStorageService) {
  }

  ngOnInit(): void {
    this.userRoles = this.tokenStorageService.getUser().roles;
    if (this.userRoles.includes(UserRole.ROLE_POST_OFFICE_EMPLOYEE.toString())) {
      this.rolePostOfficeEmployee = true;
    } else if (this.userRoles.includes(UserRole.ROLE_DEPOT_EMPLOYEE.toString())) {
      this.roleDepotEmployee = true;
    }
    this.getInvoiceList();
  }

  onSubmit(id: string) {
    if(this.rolePostOfficeEmployee){
      this.invoiceService.addInvoice(this.postDepotInvoice).subscribe(
        (res) => {
          this.getInvoiceList();
          this.toast.success('Đã thêm đơn hàng cho khách', 'Thành công');
        },
        (error) => {
          console.log(this.sendParcelList)
          this.toast.error('Đã xảy ra lỗi khi thêm đơn hàng', 'Lỗi');
        }
      );
      
    } else {
      this.confirmService.confirmPostToDepot(id).subscribe(
        (res) => {
          this.getInvoiceList();
          this.toast.success('Xác nhận đơn hàng thành công', 'Thành công');
        },
        (error) => {
          console.log(this.sendParcelList)
          this.toast.error('Đã xảy ra lỗi khi thêm đơn hàng', 'Lỗi');
        }
      );
    }

    
  }
  
  getInvoiceList() {
    if(this.rolePostOfficeEmployee){
      this.invoiceService.getConfirmDepotToPostList().subscribe(res => {
        this.tagList = res;
        console.log(res)
      });
    } else if(this.roleDepotEmployee){
      this.invoiceService.getConfirmPostToDepotList().subscribe(res => {
        this.tagList = res;
        console.log(res)
      });
    }
    
  }
}

  

  



