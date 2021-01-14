import { Injectable } from '@angular/core';
import { Intervention } from '../interfaces/intervention.interface';
import { IncidentService } from './incident.service';

@Injectable({
  providedIn: 'root',
})
export class InterventionService {

  constructor(private incidentService: IncidentService) { }

  /**
   * Return an intervention to string
   * @param intervention 
   */
  public toString(intervention: Intervention): string {
    let s: string = '';
    if (intervention) {
      s = '<table><tbody>';
      s += '<tr><td>Coefficient</td><td>'+intervention.coefficient+'</td></tr>';
      s += '<tr><td>Incident</td><td>'+this.incidentService.toString(intervention.incident)+'</td></tr>';
      s += '<tr><td>Nombre d\'agents</td><td>'+intervention.number_agents+'</td></tr>';
      s += '<tr><td>Nombre de véhicules</td><td>'+intervention.number_vehicles+'</td></tr>';
      s += '</tbody></table>';
    }

    return s;

  }

}