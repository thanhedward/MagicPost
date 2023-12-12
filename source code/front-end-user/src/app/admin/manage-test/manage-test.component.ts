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
  examList: Exam[] = [];
  transactionList: Location[] = [];
  paginationDetail: PaginationDetail;
  skeleton = true;
  now = moment(new Date()).format('YYYY-MM-DD HH:mm:ss');

  pageOptions: any = [
    {display: 1, num: 1},
    {display: 20, num: 20},
    {display: 50, num: 50},
    {display: 100, num: 100},
    {display: 'Tất cả', num: ''},
  ];
  pageCountShowing = 20;

  constructor(private locationService: LocationService) {
  }

  ngOnInit(): void {
    this.fetchTransactionList();
  }

  fetchTransactionList() {
    this.locationService.getTransaction(0, 20).subscribe(res => {
      this.transactionList = res.data;
      console.log(res.data);
      this.skeleton = false;
    });
  };

  changePageShow(value: any) {
    this.pageCountShowing = value;
    if (!value) {
      this.skeleton = true;
      this.locationService.getTransaction(0,this.paginationDetail.totalCount).subscribe(res => {
        this.transactionList = res.data;
        this.paginationDetail = res.paginationDetails;
        this.skeleton = false;
      });;
    } else {
      this.skeleton = true;
      this.locationService.getTransaction(0, value).subscribe(res => {
        this.transactionList = res.data;
        this.paginationDetail = res.paginationDetails;
        this.skeleton = false;
      });
    }
  }

  goPreviousPage() {
    const isFirstPage: boolean = this.paginationDetail.isFirstPage;
    if (!isFirstPage) {
      this.locationService.getTransaction(this.paginationDetail.previousPage.pageNumber, this.pageCountShowing)
        .subscribe(res => {
          this.transactionList = res.data;
          this.paginationDetail = res.paginationDetails;
        });
    }

  }

  goNextPage() {
    const isLastPage = !this.paginationDetail.nextPage.available;
    if (!isLastPage) {
      this.locationService.getTransaction(this.paginationDetail.nextPage.pageNumber, this.pageCountShowing
        ).subscribe(res => {
          this.transactionList = res.data;
          this.paginationDetail = res.paginationDetails;
        });
    }
  }
}
