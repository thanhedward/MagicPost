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
  selector: 'add-post-invoice',
  templateUrl: './add-post-invoice.component.html',
  styleUrls: ['./add-post-invoice.component.scss']
})
export class AddPostInvoiceComponent implements OnInit {

  rfAddD: FormGroup;
  sendParcel: sendParcel;
  sendParcelList: sendParcel[] = [];
  postDepotInvoice: postDepotInvoice;
  tagList: any[] = [];
  rolePostOfficeEmployee: boolean = false;
  roleDepotEmployee: boolean = false;
  userRoles: string[] = [];
  optionds: any[] = [];
  selectedOption: string;
  constructor(
    private fb: FormBuilder,
    private invoiceService: InvoiceService,
    private toast: ToastrService,
    private parcelService: ParcelService,
    private tokenStorageService: TokenStorageService,
    private addressService: AddressService) {
  }

  get district(){
    return this.rfAddD.get('district');
   
  }
  ngOnInit(): void {
    
    this.getDepotHasParcel();
    this.parcelService.getParceltoEachPost(this.district.value).subscribe(res => {
        this.tagList = res;
        console.log(res)
      });
    this.rfAddD = this.fb.group({
      district: ['']
    });
    
    
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
    console.log(this.district.value)
    this.invoiceService.addInvoiceDepotToPost(this.postDepotInvoice).subscribe(
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
  
  getParcelList() {
    this.parcelService.getParceltoEachPost(this.district.value).subscribe(res => {
      this.tagList = res;
      console.log(res)
    });
    
  }
  getDepotHasParcel(){
    this.addressService.getDistrictHasParcel().subscribe(res => {
      this.optionds = res;
      console.log(res)
    });
  }
}

  

  



