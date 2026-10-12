import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {OrderInterface, OrderRequestInterface} from '../interfaces/order.interface';

@Injectable({providedIn: 'root'})
export class OrderService {
  private readonly http = inject(HttpClient);

  create(order: OrderRequestInterface): Observable<OrderInterface> {
    return this.http.post<OrderInterface>(`${environment.apiUrl}/order`, order);
  }
}
