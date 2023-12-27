import {UserRole} from './user-role';
import {UserProfile} from './user-profile';

export class Manager {
    id: number;
    username: string;
    email: string;
    deleted: boolean;
    createdDate: string;
    depot: boolean;
    postOffice: any;
    roles: UserRole[];
    profile: UserProfile;

    constructor(username: string, email: string, profile: UserProfile) {
        this.username = username;
        this.email = email;
        this.profile = profile;
    }
}