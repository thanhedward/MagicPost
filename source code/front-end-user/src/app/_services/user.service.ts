import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {UserAccount} from '../models/user-account';
import {PageResult} from '../models/page-result';
import {AbstractControl, AsyncValidatorFn, ValidationErrors} from '@angular/forms';
import {environment} from '../../environments/environment';
import {map} from 'rxjs/operators';
import {UserUpdate} from '../models/user-update';
import { ParcelTracking } from '../models/parcelTracking';
import { Depot } from '../models/depot';
import { Manager } from '../models/manager';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl: string = environment.apiEndPoint;

  constructor(private http: HttpClient) {
  }

  getTracking(idParcel: number): Observable<ParcelTracking> {
    return this.http.get<ParcelTracking>(`http://localhost:8080/api/parcel/tracking`);
  }

  getUsers(status: boolean): Observable<PageResult<UserAccount>> {
    return this.http.get<PageResult<UserAccount>>(`${this.baseUrl}/users`);
  }

  getUserList(page: number, size: number): Observable<PageResult<UserAccount>> {
    const pageParams = new HttpParams().set('page', page.toString()).set('size', size.toString());
    return this.http.get<PageResult<UserAccount>>(`${this.baseUrl}/users`, {params: pageParams});
  }

  getUserListByDepot(page: number, size: number): Observable<PageResult<UserAccount>> {
    const pageParams = new HttpParams().set('page', page.toString()).set('size', size.toString());
    return this.http.get<PageResult<UserAccount>>(`${this.baseUrl}/users/get/depot`, {params: pageParams});
  }

  getUserListByPost(page: number, size: number): Observable<PageResult<UserAccount>> {
    const pageParams = new HttpParams().set('page', page.toString()).set('size', size.toString());
    return this.http.get<PageResult<UserAccount>>(`${this.baseUrl}/users/get/post-office`, {params: pageParams});
  }

  getManagerList(page: number, size: number): Observable<PageResult<Manager>> {
    const pageParams = new HttpParams().set('page', page.toString()).set('size', size.toString());
    return this.http.get<PageResult<Manager>>(`${this.baseUrl}/users/get-manager/post-office`, {params: pageParams});
  }

  // // @ts-ignore
  // getUserListDeletedByPage(page: number, size: number, status: boolean): Observable<PageResult<UserAccount>> {
  //   const pageParams = new HttpParams().set('page', page.toString()).set('size', size.toString());
  //   return this.http.get<PageResult<UserAccount>>(`${this.baseUrl}/users/deleted/${status}`, {params: pageParams});
  // }

  getDefaultAvatar() {
    return '../../assets/images/avatar-default.png';
  }

  searchUserList(page: number, size: number, searchKey: string): Observable<PageResult<UserAccount>> {
    const pageParams = new HttpParams().set('page', page.toString()).set('size', size.toString()).set('search-keyword', searchKey.toString());
    return this.http.get<PageResult<UserAccount>>(`${this.baseUrl}/users/search`, {params: pageParams});
  }

  exportExcel(status: boolean): Observable<any> {
    let headers = new HttpHeaders();
    headers = headers.set('Accept', 'application/csv');
    return this.http.get(`${this.baseUrl}/users/deleted/${status}/export/users.csv`, {
      headers,
      responseType: 'text'
    });
  }

  addUser(user: UserAccount): Observable<UserAccount> {
    return this.http.post<UserAccount>(`${this.baseUrl}/users`, user);
  }

  addDepotManager(user: UserAccount, province: string): Observable<UserAccount> {
    return this.http.post<UserAccount>(`${this.baseUrl}/users/create/depot-manager?provinceName=${province}`, user);
  }
  addPostOfficeManager(user: UserAccount, province: string, district: string){
    return this.http.post<UserAccount>(`${this.baseUrl}/users/create/post-office-manager?provinceName=${province}&districtName=${district}`, user);
  }

  addDepotEmployee(user: UserAccount): Observable<UserAccount> {
    return this.http.post<UserAccount>(`${this.baseUrl}/users/create/depot`, user);
  }

  addPostOfficeEmployee(user: UserAccount): Observable<UserAccount> {
    return this.http.post<UserAccount>(`${this.baseUrl}/users/create/post-office`, user);
  }

  deleteUser(id: number, deleted: boolean): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/users/${id}/deleted/${deleted}`);
  }

  updateUser(id: number, user: UserUpdate): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/users/${id}`, user);
  }

  validateUsername(): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      return this.http.get<boolean>(`${this.baseUrl}/users/check-username?value=${control.value}`).pipe(
        map(res => {
          // if res is true, username exists, return true
          return res ? {usernameExists: true} : null;
          // NB: Return null if there is no error
        })
      );
    };
  }

  validateEmail(): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      return this.http.get<boolean>(`${this.baseUrl}/users/check-email?value=${control.value}`).pipe(
        map(res => {
          // if res is true, username exists, return true
          return res ? {emailExists: true} : null;
          // NB: Return null if there is no error
        })
      );
    };
  }

  validateEmailUpdate(id: number): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      return this.http.get<boolean>(`${this.baseUrl}/users/${id}/check-email?value=${control.value}`).pipe(
        map(res => {
          // if res is true, username exists, return true
          return res ? {emailExists: true} : null;
          // NB: Return null if there is no error
        })
      );
    };
  }


  /**
   *not yet delete
   * @param username
   */
  getUserInfo(username?: string): Observable<any> {
    const userParams = new HttpParams().set('username', username);
    return this.http.get<any>(`${this.baseUrl}/users/profile`, {params: userParams});
  }

  updateEmail(id: number, data: any): Observable<any> {
    return this.http.patch<any>(`${this.baseUrl}/users/${id}/email/updating`, data);
  }

  updatePassword(id: number, data: any): Observable<any> {
    return this.http.patch<any>(`${this.baseUrl}/users/${id}/password/updating`, data);
  }


}
