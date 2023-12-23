import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {TokenStorageService} from '../../_services/token-storage.service';
import {UserService} from '../../_services/user.service';
import {UserProfile} from '../../models/user-profile';

@Component({
  selector: 'app-left-side',
  templateUrl: './left-side.component.html',
  styleUrls: ['./left-side.component.scss']
})
export class LeftSideComponent{
}
