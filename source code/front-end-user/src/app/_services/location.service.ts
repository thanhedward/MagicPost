import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import { Location } from '../models/location';
import {PageResult} from '../models/page-result';

@Injectable({
  providedIn: 'root'
})
export class LocationService {

  private baseUrl: string = environment.apiEndPoint;

  constructor(private http: HttpClient) {
  }

  public getTransaction():  Observable<any[]>  {
    return this.http.get<any[]>(`${this.baseUrl}/post-office/get-post-office`);

  }

  public getDepot():  Observable<any[]>  {
    return this.http.get<any[]>(`${this.baseUrl}/depot/get-depote`);

  }
  public addTransactionLocation(location: Location): Observable<Location> {
    return this.http.post<Location>(`${this.baseUrl}/location/add-transaction-office`, location);
  }

  public addPostOffice(province:string, district:string): Observable<any>{
    return this.http.post<any>(`${this.baseUrl}/post-office/create-post-office?provinceName=${province}&districtName=${district}`,'');
  }

  public addDepot(province:string): Observable<any>{
    return this.http.post<any>(`${this.baseUrl}/depot/create-depot?provinceName=${province}`,'');
  }

  
}