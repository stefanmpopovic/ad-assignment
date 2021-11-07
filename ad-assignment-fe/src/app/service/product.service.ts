import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { AppConfigService } from './app-config.service';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  baseUrl: string;

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private appConfigService: AppConfigService, private http: HttpClient) {
    this.baseUrl = appConfigService.appConfig.apiBaseUrl + 'product';
  }

  getAllProducts(): Observable<any> {
    return this.http.get(this.baseUrl);
  }

  createProduct(product: any): Observable<any> {
    return this.http.post(this.baseUrl, product, this.httpOptions);
  }

  updateProduct(id: number, product: any): Observable<any> {
    const url = this.baseUrl + '/' + id;

    return this.http.put(url, product, this.httpOptions);
  }
}
