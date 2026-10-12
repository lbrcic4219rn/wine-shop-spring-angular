import {Component, inject, signal} from '@angular/core';
import {takeUntilDestroyed} from '@angular/core/rxjs-interop';
import {CurrencyPipe} from '@angular/common';
import {MatCardModule} from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatChipListboxChange, MatChipsModule} from '@angular/material/chips';
import {MatPaginatorModule, PageEvent} from '@angular/material/paginator';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {catchError, of, Subject, switchMap, tap} from 'rxjs';
import {WineService} from '../../services/wine.service';
import {CartService} from '../../services/cart.service';
import {WineInterface} from '../../interfaces/wine.interface';
import {WineStyleInterface} from '../../interfaces/wine-style.interface';
import {WineVarietyInterface} from '../../interfaces/wine-variety.interface';

@Component({
  selector: 'app-home',
  imports: [CurrencyPipe, MatCardModule, MatButtonModule, MatIconModule, MatChipsModule, MatPaginatorModule, MatProgressBarModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  readonly wineService = inject(WineService);
  readonly styles = signal<WineStyleInterface[]>([]);
  readonly varieties = signal<WineVarietyInterface[]>([]);
  readonly wines = signal<WineInterface[]>([]);
  readonly totalElements = signal(0);
  readonly pageIndex = signal(0);
  readonly pageSize = signal(12);
  readonly loading = signal(true);
  readonly error = signal(false);
  private readonly cart = inject(CartService);
  private styleIds: number[] = [];
  private varietyIds: number[] = [];
  private readonly reload = new Subject<void>();

  constructor() {
    this.wineService.getStyles().pipe(takeUntilDestroyed()).subscribe({
      next: styles => this.styles.set(styles),
      error: () => this.error.set(true)
    });
    this.wineService.getVarieties().pipe(takeUntilDestroyed()).subscribe({
      next: varieties => this.varieties.set(varieties),
      error: () => this.error.set(true)
    });

    // switchMap drops the response of a superseded search when filters change quickly
    this.reload.pipe(
      tap(() => {
        this.loading.set(true);
        this.error.set(false);
      }),
      switchMap(() => this.wineService.search(this.styleIds, this.varietyIds, this.pageIndex(), this.pageSize()).pipe(
        catchError(() => {
          this.error.set(true);
          return of(null);
        })
      )),
      takeUntilDestroyed()
    ).subscribe(page => {
      this.loading.set(false);
      if (page) {
        this.wines.set(page.content);
        this.totalElements.set(page.totalElements);
      }
    });

    this.search();
  }

  onStylesChange(change: MatChipListboxChange): void {
    this.styleIds = change.value ?? [];
    this.pageIndex.set(0);
    this.search();
  }

  onVarietiesChange(change: MatChipListboxChange): void {
    this.varietyIds = change.value ?? [];
    this.pageIndex.set(0);
    this.search();
  }

  onPage(event: PageEvent): void {
    this.pageIndex.set(event.pageIndex);
    this.pageSize.set(event.pageSize);
    this.search();
  }

  addToOrder(wine: WineInterface): void {
    this.cart.add(wine);
  }

  search(): void {
    this.reload.next();
  }
}
