import {Component, OnInit} from '@angular/core';
import { FormBuilder, FormGroup, FormArray } from '@angular/forms';
import {ToastrService} from 'ngx-toastr';
import { ParcelService } from 'src/app/_services/parcel.service';
import { InvoiceService } from 'src/app/_services/invoice.service';
import { sendParcel } from 'src/app/models/send-parcel';
import { postDepotInvoice } from 'src/app/models/post-depot-invoice';
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
  constructor(
    private fb: FormBuilder,
    private invoiceService: InvoiceService,
    private toast: ToastrService,
    private parcelService: ParcelService) {
  }
  ngOnInit(): void {
    this.rfAdd = this.fb.group({
    });
    this.getParcelList();
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
  }
  
  getParcelList() {
    this.parcelService.getParcelList().subscribe(res => {
      this.tagList = res;
      console.log(res)
    });
  }
}

  

  



