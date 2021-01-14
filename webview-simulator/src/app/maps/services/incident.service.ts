import { Injectable } from '@angular/core';
import { Incident } from '../interfaces/incident.interface';
import { LocationeService } from './locatione.service';
import { SensorService } from './sensor.service';

@Injectable({
  providedIn: 'root',
})
export class IncidentService {

  constructor(private locationeService: LocationeService, private sensorService: SensorService) { }

  /**
   * Return an incident to string
   * @param incident 
   */
  public toString(incident: Incident): string {
    let s: string = '';
    if (incident) {
      s = '<table><tbody>';
      s += '<tr><td>Code incident</td><td>'+incident.code_incident+'</td></tr>';
      s += '<tr><td>Intensité</td><td>'+incident.intensity+'</td></tr>';
      s += '<tr><td>Location</td><td>'+this.locationeService.toString(incident.location)+'</td></tr>';
      if (incident.sensor) {
        s += '<tr><td>Sensor</td><td>'+this.sensorService.toString(incident.sensor)+'</td></tr>';
      }
      s += '<tr><td>Type</td><td>'+incident.type+'</td></tr>';
      s += '</tbody></table>';
    }

    return s;

  }

  /**
   * Return type of fire in function of intensity
   * @param incident 
   */
  public computeType(incident: Incident): string {
    let type: string = 'fire';
    if (incident.intensity < 4) {
      type = 'fireSmall';
    } else if (incident.intensity > 7) {
      type = 'fireHuge';
    }

    return type;

  }

}