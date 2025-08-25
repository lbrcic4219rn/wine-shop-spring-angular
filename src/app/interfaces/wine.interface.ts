import {WineStyleInterface} from './wine-style.interface';
import {WineVarietyInterface} from './wine-variety.interface';

export interface WineInterface {
  id: number;
  name: string;
  price: number;
  style: WineStyleInterface;
  variety: WineVarietyInterface;
  imageLocation: string;
}


