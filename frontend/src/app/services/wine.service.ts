import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {WineInterface} from '../interfaces/wine.interface';
import {WineStyleInterface} from '../interfaces/wine-style.interface';
import {WineVarietyInterface} from '../interfaces/wine-variety.interface';
import {PageInterface} from '../interfaces/page.interface';

@Injectable({providedIn: 'root'})
export class WineService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = environment.apiUrl;

  getStyles(): Observable<WineStyleInterface[]> {
    return this.http.get<WineStyleInterface[]>(`${this.apiUrl}/wine-style`);
  }

  getVarieties(): Observable<WineVarietyInterface[]> {
    return this.http.get<WineVarietyInterface[]>(`${this.apiUrl}/wine-variety`);
  }

  search(styleIds: number[], varietyIds: number[], page: number, size: number): Observable<PageInterface<WineInterface>> {
    let params = new HttpParams().set('page', page).set('size', size);
    if (styleIds.length) {
      params = params.set('styleIds', styleIds.join(','));
    }
    if (varietyIds.length) {
      params = params.set('varietyIds', varietyIds.join(','));
    }
    return this.http.get<PageInterface<WineInterface>>(`${this.apiUrl}/wine`, {params});
  }

  imageUrl(wine: WineInterface): string {
    return `${this.apiUrl}/images/${wine.imageLocation}`;
  }
}
