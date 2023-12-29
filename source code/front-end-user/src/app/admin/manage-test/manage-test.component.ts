import {Component, OnInit} from '@angular/core';
import {Exam} from '../../models/exam';
import {PaginationDetail} from '../../models/pagination/pagination-detail';
import { LocationService } from 'src/app/_services/location.service';
import { Location } from 'src/app/models/location';
import * as moment from 'moment';

@Component({
  selector: 'app-manage-test',
  templateUrl: './manage-test.component.html',
  styleUrls: ['./manage-test.component.scss']
})
export class ManageTestComponent implements OnInit {
  transactionList: any[] = [];
  skeleton = true;
  constructor(private locationService: LocationService) {
  }

  ngOnInit(): void {
    this.fetchTransactionList();
  }

  fetchTransactionList() {
    this.locationService.getTransaction().subscribe(res => {
      this.transactionList = res;
      this.skeleton = false;
    });
  };
}
