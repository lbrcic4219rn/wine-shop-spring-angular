import {TestBed} from '@angular/core/testing';
import {CartService, MAX_QUANTITY} from './cart.service';
import {WineInterface} from '../interfaces/wine.interface';

const wine = (id: number, price: number): WineInterface => ({
  id,
  name: `Wine ${id}`,
  price,
  imageLocation: 'wine1.jpg',
  style: {id: 1, name: 'Belo'},
  variety: {id: 1, name: 'Rizling'}
});

describe('CartService', () => {
  let cart: CartService;

  beforeEach(() => {
    localStorage.clear();
    cart = TestBed.inject(CartService);
  });

  it('adds a wine once and increments quantity on repeat', () => {
    cart.add(wine(1, 100));
    cart.add(wine(1, 100));
    cart.add(wine(2, 250.5));

    expect(cart.items()).toHaveSize(2);
    expect(cart.count()).toBe(3);
    expect(cart.total()).toBe(450.5);
  });

  it('removes the line when quantity drops below one', () => {
    cart.add(wine(1, 100));
    cart.setQuantity(1, 0);

    expect(cart.items()).toEqual([]);
  });

  it('caps quantity at the maximum the API accepts', () => {
    cart.add(wine(1, 100));
    cart.setQuantity(1, MAX_QUANTITY + 5);

    expect(cart.items()[0].quantity).toBe(MAX_QUANTITY);
  });

  it('persists the cart to local storage', () => {
    cart.add(wine(1, 100));
    TestBed.flushEffects();

    expect(JSON.parse(localStorage.getItem('fpis-fondrum-cart')!)).toHaveSize(1);
  });
});
