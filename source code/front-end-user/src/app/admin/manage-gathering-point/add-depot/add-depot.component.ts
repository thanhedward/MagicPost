import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {ToastrService} from 'ngx-toastr';
import {Router} from '@angular/router';
import { LocationService } from 'src/app/_services/location.service';
import { AddressService } from 'src/app/_services/address.service';

@Component({
  selector: 'app-add-depot',
  templateUrl: './add-depot.component.html',
  styleUrls: ['./add-depot.component.scss']
})
export class AddDepotComponent implements OnInit {

  rfAdd: FormGroup;
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
  ngOnInit(): void {
    this.rfAdd = this.fb.group({
      provincePost: [''],
      }
    );
    this.getDepotProvince();
  }
  onSubmit() {
    console.log(1)
    this.locationService.addDepot(this.provincePost.value.toString()).subscribe(res => {
      this.toast.success('Đã thêm điểm tập kết', 'Thành công');
      setTimeout(() => this.goToExamManagePage(), 1000);
    });
  }
  goToExamManagePage() {
    this.router.navigate(['/admin/tests']);
  }
  getDepotProvince() {
    this.addressService.getNewProvince().subscribe(res => {
      this.depotProvinceList = res;
      console.log(res)
    });
  }
}

  

  



