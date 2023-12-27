// address.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AddressService {
  private baseUrl: string = environment.apiEndPoint;
  private apiProvincesUrl = 'https://magicpost-uet.onrender.com/api/administrative/province/getAll';
  private apiDistrictsUrl = 'https://magicpost-uet.onrender.com/api/administrative/district/getAll/';
  constructor(private http: HttpClient) {}

  getProvinces(): Observable<any[]> {
    return this.http.get<any[]>(this.apiProvincesUrl);
  }
  getDistricts(provinceId: string): Observable<any[]> {
    return this.http.get<any[]>(this.apiDistrictsUrl + provinceId);
  }
  getDepot(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/depot/get-depot`);
  }
  getPostOffice(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/post-office/get-post-office`);
  }
}
