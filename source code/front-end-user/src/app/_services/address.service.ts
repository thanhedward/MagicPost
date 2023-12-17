// address.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AddressService {
  private apiProvincesUrl = 'https://magicpost-uet.onrender.com/api/administrative/province/getAll';
  private apiDistrictsUrl = 'https://magicpost-uet.onrender.com/api/administrative/district/getAll/';
  constructor(private http: HttpClient) {}

  getProvinces(): Observable<any[]> {
    return this.http.get<any[]>(this.apiProvincesUrl);
  }
  getDistricts(provinceId: string): Observable<any[]> {
    return this.http.get<any[]>(this.apiDistrictsUrl + provinceId);
  }
}
