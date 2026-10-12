import {computed, effect, Injectable, signal} from '@angular/core';
import {OrderItemInterface} from '../interfaces/order-item.interface';
import {WineInterface} from '../interfaces/wine.interface';

const STORAGE_KEY = 'fpis-fondrum-cart';
export const MAX_QUANTITY = 1000;

@Injectable({providedIn: 'root'})
export class CartService {
  readonly items = signal<OrderItemInterface[]>(this.restore());
  readonly count = computed(() => this.items().reduce((sum, item) => sum + item.quantity, 0));
  readonly total = computed(() => this.items().reduce((sum, item) => sum + item.wine.price * item.quantity, 0));

  constructor() {
    effect(() => {
      try {
        localStorage.setItem(STORAGE_KEY, JSON.stringify(this.items()));
      } catch {
        // Storage unavailable (private mode, quota); the cart still works for this visit
      }
    });
  }

  add(wine: WineInterface): void {
    const existing = this.items().find(item => item.wine.id === wine.id);
    if (existing) {
      this.setQuantity(wine.id, existing.quantity + 1);
    } else {
      this.items.update(items => [...items, {wine, quantity: 1}]);
    }
  }

  setQuantity(wineId: number, quantity: number): void {
    if (quantity < 1) {
      this.remove(wineId);
      return;
    }
    const clamped = Math.min(quantity, MAX_QUANTITY);
    this.items.update(items => items.map(item => item.wine.id === wineId ? {...item, quantity: clamped} : item));
  }

  remove(wineId: number): void {
    this.items.update(items => items.filter(item => item.wine.id !== wineId));
  }

  clear(): void {
    this.items.set([]);
  }

  private restore(): OrderItemInterface[] {
    try {
      const stored = JSON.parse(localStorage.getItem(STORAGE_KEY) ?? '[]');
      return Array.isArray(stored) ? stored : [];
    } catch {
      return [];
    }
  }
}
