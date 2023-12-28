import {UserRole} from './user-role';
import {UserProfile} from './user-profile';
import {Intake} from './intake';
import { Depot } from './depot';
import { PostOffice } from './postoffice';

export class UserAccount {
  id: number;
  username: string;
  email: string;
  enabled: boolean;
  deleted: boolean;
  createdDate: Date;
  roles: UserRole[];
  depot: Depot;
  postOffice: PostOffice;
  profile: UserProfile;
  intake: Intake;


  constructor(username: string, email: string, profile: UserProfile) {
    this.username = username;
    this.email = email;
    this.profile = profile;
  }
}
