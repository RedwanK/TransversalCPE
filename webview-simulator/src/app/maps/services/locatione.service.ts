import { Injectable } from '@angular/core';
import { Locatione } from '../interfaces/locatione.interface';
import { CityService } from './city.service';

@Injectable({
  providedIn: 'root',
})
export class LocationeService {

  constructor(private cityService: CityService) { }

  /**
   * Return an location to string
   * @param location 
   */
  public toString(location: Locatione): string {
    let s: string = '';
    if (location) {
      s = '<table><tbody>';
      if (location.city) {
        s += '<tr><td>Ville</td><td>'+this.cityService.toString(location.city)+'</td></tr>';
      }
      s += '<tr><td>Latitude</td><td>'+location.latitude+'</td></tr>';
      s += '<tr><td>Longitude</td><td>'+location.longitude+'</td></tr>';
      s += '</tbody></table>';
    }

    return s;

  }

}