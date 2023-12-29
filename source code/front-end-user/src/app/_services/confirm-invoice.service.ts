import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import { Parcel } from '../models/parcel';

@Injectable({
  providedIn: 'root'
})
export class ConfirmService {

  private baseUrl: string = environment.apiEndPoint;

  constructor(private http: HttpClient) {
  }
  public confirmPostToDepot(id: string): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/invoice/confirm/${id}`," ");
  }
  
  public getParceltoPostList(): Observable<Parcel[]> {
    return this.http.get<Parcel[]>(`${this.baseUrl}/parcel/get/depot/depot`);
  }

}