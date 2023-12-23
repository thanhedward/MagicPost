import {Component, OnInit} from '@angular/core';
import { AddressService } from 'src/app/_services/address.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  provinceList: any[] = [];
  selectedProvince: string;
  districtList: any[] = [];
  selectedDistrict: string;



  constructor(private addressService: AddressService) {
  }


  ngOnInit(): void {
    this.getProvince();
  }

  getProvince() {
    this.addressService.getProvinces().subscribe(res => {
      this.provinceList = res;
      console.log(res)
    });
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

}
