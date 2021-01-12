import { Injectable } from '@angular/core';
import { Incident } from '../interfaces/incident.interface';
import { Marker } from '../interfaces/marker.interface';

import * as L from 'leaflet';
import { IncidentService } from './incident.service';
import { MapService } from './map.service';
import { Intervention } from '../interfaces/intervention.interface';
import { Sensor } from '../interfaces/sensor.interface';
import { SensorService } from './sensor.service';
import { InterventionService } from './intervention.service';

@Injectable({
  providedIn: 'root',
})
export class ApiService {

  protected interventions: Marker[] = [];
  protected incidents: Marker[] = [];
  protected sensors: Marker[] = [];

  protected offset: number = 0.0003;

  constructor(
    private incidentService: IncidentService, 
    private mapService: MapService,
    private sensorService: SensorService,
    private interventionService: InterventionService
  ) { }

  /**
   * Update incidents
   */
  public __updateIncidents(map: any, icds: Incident[]): void {
    // Remove all markers
    this.mapService.__deleteMarkers(map, this.incidents);
    this.incidents = [];

    // Create new markers
    icds.forEach(icd => {
      // Add a circle link to intensity
      var circle = L.circle([icd.location.latitude, icd.location.longitude], {
        radius: icd.intensity * 20,
        weight: 1,
        color: '#e15b64',
        fillColor: '#cacaca',
        fillOpacity: 0.2
      });
      this.incidents.push({
        id: icd.id,
        lat: icd.location.latitude,
        long: icd.location.longitude,
        type: null,
        popup: null,
        m: circle
      });

      // Compute type of fire
      let type: string = this.incidentService.computeType(icd);

      this.incidents.push({
        id: icd.id,
        lat: icd.location.latitude,
        long: icd.location.longitude,
        type: type,
        popup: this.incidentService.toString(icd),
        m: null
      });
      
    });

    // Add markers to map
    this.mapService.__showMarkers(map, this.incidents);
    
  }

  /**
   * Update interventions
   */
  public __updateInterventions(map: any, itvs: Intervention[]): void {
    // Remove all markers
    this.mapService.__deleteMarkers(map, this.interventions);
    this.interventions = [];

    // Create new markers
    itvs.forEach(itv => {
      this.interventions.push({
        id: itv.id,
        lat: itv.incident.location.latitude - this.offset,
        long: itv.incident.location.longitude - this.offset,
        type: 'fireTruck',
        popup: this.interventionService.toString(itv),
        m: null
      });
      
    });

    // Add markers to map
    this.mapService.__showMarkers(map, this.interventions);
    
  }

  /**
   * Update sensors
   */
  public __updateSensors(map: any, sens: Sensor[]): void {
    // Remove all markers
    this.mapService.__deleteMarkers(map, this.sensors);
    this.sensors = [];

    // Create new markers
    sens.forEach(sen => {
      this.sensors.push({
        id: sen.id,
        lat: sen.location.latitude + this.offset,
        long: sen.location.longitude + this.offset,
        type: 'sensor',
        popup: this.sensorService.toString(sen),
        m: null
      });
      
    });

    // Add markers to map
    this.mapService.__showMarkers(map, this.sensors);
    
  }

}