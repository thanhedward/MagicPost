import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ToastrService} from 'ngx-toastr';
import {Router} from '@angular/router';
import { LocationService } from 'src/app/_services/location.service';
import { Location } from 'src/app/models/location';
import { AddressService } from 'src/app/_services/address.service';
import { Parcel } from 'src/app/models/parcel';
import { ParcelService } from 'src/app/_services/parcel.service';
@Component({
  selector: 'add-parcel-test',
  templateUrl: './add-parcel.component.html',
  styleUrls: ['./add-parcel.component.scss']
})
export class AddParcelComponent implements OnInit {

  rfAdd: FormGroup;
  districtList: any[] = [];
  depotProvinceList: any[] = [];
  constructor(
    private fb: FormBuilder,
    private locationService: LocationService,
    private toast: ToastrService,
    private router: Router,
    private addressService: AddressService,
    private parcelService: ParcelService) {
  }
  get name(){
    return this.rfAdd.get('name');
  }
  get sender(){
    return this.rfAdd.get('sender');
  }
  get startAddress(){
    return this.rfAdd.get('startAddress');
  }
  get endAddress(){
    return this.rfAdd.get('endAddress');
  }
  get weight(){
    return this.rfAdd.get('weight');
  }
  get provincePost(){
    return this.rfAdd.get('provincePost');
  }
  get districtPost(){
    return this.rfAdd.get('districtPost');
  }
  ngOnInit(): void {
    this.rfAdd = this.fb.group({
      name: ['', Validators.required],
      sender: ['', Validators.required],
      startAddress: ['', Validators.required],
      endAddress: ['', Validators.required],
      weight: ['', [Validators.required, Validators.pattern(/^[0-9]+(\.[0-9]+)?$/)]],
      provincePost: ['', Validators.required],
      districtPost: ['', Validators.required],
    });
    this.getDepotProvince();
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
  onSubmit() {
    const parcel = new Parcel(this.name.value.toString(), this.sender.value.toString(), this.startAddress.value.toString() ,this.endAddress.value.toString() ,this.weight.value , this.provincePost.value.toString() ,this.districtPost.value.toString());
    this.parcelService.addParcel(parcel).subscribe(res => {
      this.toast.success('Đã thêm đơn hàng cho khách', 'Thành công');
    });
    this.router.navigateByUrl('/pdf-invoice')
  }
  getDepotProvince() {
    this.addressService.getDepot().subscribe(res => {
      this.depotProvinceList = res;
      console.log(res)
    });
  }
}

  

  



