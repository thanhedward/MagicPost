import {Component, OnInit} from '@angular/core';
import { UserService } from 'src/app/_services/user.service';
import * as moment from 'moment';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  name: string = "SAO";
  time: Date = new Date();
  startPost: string = "Vĩnh Lộc Thanh Hóa";
  firstDepot: string = "Thanh Hóa";
  secondDepot: string = "Hà Nội";
  endPost: string = "Ba Đình, Hà Nội"

  deliverySuccess: boolean = false;
  currentStep: number = 4;

  skeletonLoading = true;
  now = moment(new Date()).format('YYYY-MM-DD HH:mm:ss');

  constructor( private userService: UserService) {
  }

  ngOnInit(): void {
    this.userService.getTracking(3).subscribe(res => {
      this.name = res.name;
      this.time = res.time;
      this.startPost = res.startPost;
      this.firstDepot = res.firstDepot;
      this.secondDepot = res.secondDepot;
      this.endPost = res.endPost;
      this.deliverySuccess = res.deliverySuccess;
      this.currentStep = res.currentStep;
    });

  }

}

