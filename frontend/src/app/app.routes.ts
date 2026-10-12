import {Routes} from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./pages/home/home.component').then(m => m.HomeComponent)
  }, {
    path: 'order',
    loadComponent: () => import('./pages/order-page/order-page.component').then(m => m.OrderPageComponent)
  }, {
    path: '**',
    redirectTo: ''
  }
];
