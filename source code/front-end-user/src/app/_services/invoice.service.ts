import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import { postDepotInvoice } from '../models/post-depot-invoice';

@Injectable({
  providedIn: 'root'
})
export class InvoiceService {

  private baseUrl: string = environment.apiEndPoint;

  constructor(private http: HttpClient) {
  }
  public addInvoice(invoice: postDepotInvoice): Observable<postDepotInvoice> {
    return this.http.post<postDepotInvoice>(`${this.baseUrl}/invoice/create/post-office/depot`, invoice);
  }

}