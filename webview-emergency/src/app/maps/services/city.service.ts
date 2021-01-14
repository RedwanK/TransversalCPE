import { Injectable } from '@angular/core';
import { City } from '../interfaces/city.interface';

@Injectable({
  providedIn: 'root',
})
export class CityService {

  constructor() { }

  /**
   * Return an city to string
   * @param city 
   */
  public toString(city: City): string {
    let s: string = '';
    if (city) {
      s = '<table><tbody>';
      s += '<tr><td>Nom</td><td>'+city.name+'</td></tr>';
      s += '</tbody></table>';
    }

    return s;

  }

}