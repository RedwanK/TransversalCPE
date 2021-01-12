import { Injectable } from '@angular/core';
import { Sensor } from '../interfaces/sensor.interface';
import { LocationeService } from './locatione.service';

@Injectable({
  providedIn: 'root',
})
export class SensorService {

  constructor(private locationeService: LocationeService) { }

  /**
   * Return an sensor to string
   * @param sensor 
   */
  public toString(sensor: Sensor): string {
    let s: string;
    s = '<table><tbody>';
    s += '<tr><td>Location</td><td>'+this.locationeService.toString(sensor.location)+'</td></tr>';
    s += '<tr><td>Nom</td><td>'+sensor.name+'</td></tr>';
    s += '<tr><td>Référence</td><td>'+sensor.reference+'</td></tr>';
    s += '<tr><td>Type</td><td>'+sensor.type+'</td></tr>';
    s += '</tbody></table>';

    return s;

  }

}