import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {ToastrService} from 'ngx-toastr';
import {Router} from '@angular/router';
import { LocationService } from 'src/app/_services/location.service';
import { Location } from 'src/app/models/location';

@Component({
  selector: 'app-add-depot',
  templateUrl: './add-depot.component.html',
  styleUrls: ['./add-depot.component.scss']
})
export class AddDepotComponent implements OnInit {

  rfAdd: FormGroup;
  constructor(
    private fb: FormBuilder,
    private locationService: LocationService,
    private toast: ToastrService,
    private router: Router) {
  }

  get name() {
    return this.rfAdd.get('name');
  }

  get id(){
    return this.rfAdd.get('id');
  }
  ngOnInit(): void {
    this.rfAdd = this.fb.group({
        name: [''],
        id: [''],
      }
    );
  }

  onSubmit() {
     const newTransactionLocation = new Location(
      this.name.value,
      "TRANSACTION_OFFICE"
    );
    this.locationService.addTransactionLocation(newTransactionLocation).subscribe(res => {
      this.toast.success('Đã thêm điểm giao dịch', 'Thành công');
     
    });
  }
}

  

  



