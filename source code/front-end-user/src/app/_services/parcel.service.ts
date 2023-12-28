import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import { Location } from '../models/location';
import {PageResult} from '../models/page-result';
import { Parcel } from '../models/parcel';

@Injectable({
  providedIn: 'root'
})
export class ParcelService {

  private baseUrl: string = environment.apiEndPoint;

  constructor(private http: HttpClient) {
  }
  public addParcel(parcel: Parcel): Observable<Parcel> {
    return this.http.post<Parcel>(`${this.baseUrl}/parcel/create-parcel`, parcel);
  }
  public getParcelList(): Observable<Parcel[]> {
    return this.http.get<Parcel[]>(`${this.baseUrl}/parcel/get/post-office/depot`);
  }
  public getParceltoPostList(): Observable<Parcel[]> {
    return this.http.get<Parcel[]>(`${this.baseUrl}/parcel/get/depot/depot`);
  }

}