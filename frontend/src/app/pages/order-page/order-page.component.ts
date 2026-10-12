import {Component, inject, signal} from '@angular/core';
import {CurrencyPipe} from '@angular/common';
import {FormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import {RouterLink} from '@angular/router';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {CartService} from '../../services/cart.service';
import {OrderService} from '../../services/order.service';
import {WineService} from '../../services/wine.service';
import {OrderInterface} from '../../interfaces/order.interface';

@Component({
  selector: 'app-order-page',
  imports: [CurrencyPipe, ReactiveFormsModule, RouterLink, MatButtonModule, MatIconModule, MatFormFieldModule, MatInputModule],
  templateUrl: './order-page.component.html',
  styleUrl: './order-page.component.css'
})
export class OrderPageComponent {
  readonly cart = inject(CartService);
  readonly wineService = inject(WineService);
  readonly form = inject(FormBuilder).nonNullable.group({
    customerName: ['', [Validators.required, Validators.pattern(/\S/)]],
    customerEmail: ['', [Validators.required, Validators.email]]
  });
  readonly submitting = signal(false);
  readonly errorMessage = signal<string | null>(null);
  readonly confirmed = signal<OrderInterface | null>(null);
  private readonly orderService = inject(OrderService);

  submit(): void {
    if (this.form.invalid || this.cart.items().length === 0 || this.submitting()) {
      this.form.markAllAsTouched();
      return;
    }

    this.submitting.set(true);
    this.errorMessage.set(null);

    this.orderService.create({
      ...this.form.getRawValue(),
      items: this.cart.items().map(item => ({wineId: item.wine.id, quantity: item.quantity}))
    }).subscribe({
      next: order => {
        this.submitting.set(false);
        this.confirmed.set(order);
        this.cart.clear();
        this.form.reset();
      },
      error: response => {
        this.submitting.set(false);
        this.errorMessage.set(response.status === 404
          ? 'A wine in your order is no longer available. Remove it and try again.'
          : 'The order could not be placed. Please try again.');
      }
    });
  }
}
