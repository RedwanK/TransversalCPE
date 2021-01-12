import { Injectable } from '@angular/core';
import { Incident } from '../interfaces/incident.interface';
import { Marker } from '../interfaces/marker.interface';

import { MapIconOptions } from '../data/map-icon-options';

import * as L from 'leaflet';
import { IncidentService } from './incident.service';
import { MapService } from './map.service';

@Injectable({
  providedIn: 'root',
})
export class ApiService {

  public incidents: Marker[] = [];

  constructor(private incidentService: IncidentService, private mapService: MapService) { }

  /**
   * Update incidents
   */
  public __updateIncidents(map: any, icds: Incident[]): void {
    // Remove all markers
    this.mapService.__deleteMarkers(map, this.incidents);
    this.incidents = [];

    // Create new markers
    icds.forEach(icd => {
      // Compute type of fire
      let type: string = this.incidentService.computeType(icd);

      this.incidents.push({
        id: icd.id,
        lat: icd.location.latitude,
        long: icd.location.longitude,
        type: type,
        popup: this.incidentService.toString(icd) ,
        m: null
      });
    });

    // Add markers to map
    this.mapService.__showMarkers(map, this.incidents);
    
  }

}