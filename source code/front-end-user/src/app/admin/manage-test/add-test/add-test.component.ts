import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {ToastrService} from 'ngx-toastr';
import {Router} from '@angular/router';
import { LocationService } from 'src/app/_services/location.service';
import { Location } from 'src/app/models/location';
import { AddressService } from 'src/app/_services/address.service';

@Component({
  selector: 'app-add-test',
  templateUrl: './add-test.component.html',
  styleUrls: ['./add-test.component.scss']
})
export class AddTestComponent implements OnInit {

  rfAdd: FormGroup;
  districtList: any[] = [];
  depotProvinceList: any[] = [];
  constructor(
    private fb: FormBuilder,
    private locationService: LocationService,
    private toast: ToastrService,
    private router: Router,
    private addressService: AddressService) {
  }
  get provincePost(){
    return this.rfAdd.get('provincePost');
  }
  get districtPost(){
    return this.rfAdd.get('districtPost');
  }
  ngOnInit(): void {
    this.rfAdd = this.fb.group({
      provincePost: [''],
      districtPost: [''],
      }
    );
    this.getDepotProvince();
  }
  onProvinceChange() {
    console.log("yes")
    if (this.provincePost.value) {
      this.addressService.getNewDistrict(this.provincePost.value).subscribe(res => {
        this.districtList = res;
      });
    } else {
      this.districtList = [];
    }
  }
  onSubmit() {
    this.locationService.addPostOffice(this.provincePost.value, this.districtPost.value).subscribe(res => {
      this.toast.success('Đã thêm điểm giao dịch', 'Thành công');
      setTimeout(() => this.goToExamManagePage(), 1000);
    });
  }
goToExamManagePage() {
    this.router.navigate(['/admin/tests']);
  }
  getDepotProvince() {
    this.addressService.getDepot().subscribe(res => {
      this.depotProvinceList = res;
      console.log(res)
    });
  }
}

  

  



