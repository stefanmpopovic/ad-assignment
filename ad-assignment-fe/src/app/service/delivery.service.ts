import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AppConfigService } from './app-config.service';

@Injectable({
  providedIn: 'root'
})
export class DeliveryService {

  baseUrl: string;

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private appConfigService: AppConfigService, private http: HttpClient) {
    this.baseUrl = appConfigService.appConfig.apiBaseUrl + '/delivery-date';
  }

  getDeliveryTime(productId: number, quantity: number, expressDelivery: boolean) {
    if (quantity < 1) {
      return of('');
    }

    const url = `${this.baseUrl}?productId=${productId}&quantity=${quantity}&expressDelivery=${!!expressDelivery}`;

    return this.http.get(url);
  }
}
