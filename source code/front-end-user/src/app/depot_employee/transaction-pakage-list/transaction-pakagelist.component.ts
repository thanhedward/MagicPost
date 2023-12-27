import {Component, OnInit} from '@angular/core';

import {PaginationDetail} from '../../models/pagination/pagination-detail';

import * as moment from 'moment';

@Component({
  selector: 'app-transaction-pakagelist',
  templateUrl: './transaction-pakagelist.component.html',
  styleUrls: ['./transaction-pakagelist.component.scss']
})
export class TransactionPakageListComponent implements OnInit {
  
  
  paginationDetail: PaginationDetail;
  skeleton = true;
  now = moment(new Date()).format('YYYY-MM-DD HH:mm:ss');

  pageOptions: any = [
    {display: 5, num: 5},
    {display: 10, num: 10},
    {display: 20, num: 20},
    {display: 50, num: 50},
    {display: 100, num: 100},
    {display: 'Tất cả', num: ''},
  ];
  pageCountShowing = 5;

  constructor() {
  }

  ngOnInit(): void {
    // this.fetchTransactionList();
  }

  

  changePageShow(value: any) {
    this.pageCountShowing = value;
    if (!value) {
      this.skeleton = true;
    //   this.locationService.getTransaction(0,this.paginationDetail.totalCount).subscribe(res => {
    //     this.transactionList = res.data;
    //     this.paginationDetail = res.paginationDetails;
    //     this.skeleton = false;
    //   });;
    } else {
      this.skeleton = true;
    //   this.locationService.getTransaction(0, value).subscribe(res => {
    //     this.transactionList = res.data;
    //     this.paginationDetail = res.paginationDetails;
    //     this.skeleton = false;
    //   });
    }
  }

  goPreviousPage() {
    const isFirstPage: boolean = this.paginationDetail.isFirstPage;
    if (!isFirstPage) {
    //   this.locationService.getTransaction(this.paginationDetail.previousPage.pageNumber, this.pageCountShowing)
    //     .subscribe(res => {
    //       this.transactionList = res.data;
    //       this.paginationDetail = res.paginationDetails;
    //     });
    }

  }

  goNextPage() {
    const isLastPage = !this.paginationDetail.nextPage.available;
    if (!isLastPage) {
    //   this.locationService.getTransaction(this.paginationDetail.nextPage.pageNumber, this.pageCountShowing
    //     ).subscribe(res => {
    //       this.transactionList = res.data;
    //       this.paginationDetail = res.paginationDetails;
    //       console.log(this.pageCountShowing)
    //     });
    }
  }
}
