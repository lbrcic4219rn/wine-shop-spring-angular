import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { OrderPageComponent } from './pages/order-page/order-page.component';

export const routes: Routes = [
    { 
        path: '',
        component: HomeComponent
    }, {
        path: 'order',
        component: OrderPageComponent
    }
];
