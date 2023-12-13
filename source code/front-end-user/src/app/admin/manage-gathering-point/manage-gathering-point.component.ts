import { Component, OnInit } from '@angular/core';
import { LocationService } from 'src/app/_services/location.service';
import { Location } from 'src/app/models/location';

@Component({
  selector: 'app-manage-gathering-point',
  templateUrl: './manage-gathering-point.component.html',
  styleUrls: ['./manage-gathering-point.scss']
})
export class ManageGatheringPointComponent implements OnInit {
  transactionList: Location[] = [];

  constructor(private locationService: LocationService) {}

  ngOnInit(): void {
    this.fetchTransactionList();
  }

  fetchTransactionList() {
    this.locationService.getTransaction(0, 20).subscribe(res => {
      this.transactionList = res.data;
    });
  }
}
