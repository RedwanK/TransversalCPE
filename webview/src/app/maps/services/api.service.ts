import { Injectable } from '@angular/core';
import { Incident } from '../interfaces/incident.interface';
import { Marker } from '../interfaces/marker.interface';

import { MapIconOptions } from '../data/map-icon-options';

import * as L from 'leaflet';

@Injectable({
  providedIn: 'root',
})
export class ApiService {

  public incidents: Incident[] = [];

  public markers: Marker[] = [];

  constructor() { }

  /**
   * Update incidents
   */
  public __updateIncidents(map: any, icds: Incident[]): void {
    icds.forEach(icd => {
      //let icdTmp: Incident = this.incidents.find(x => x.id === icd.id);
      let find: boolean = false;

      const n: number = this.incidents.length;
      let j: number;

      for (j = 0; j < n; ++j) {
        if (this.incidents[j].id == icd.id) {
          find = true;
          this.incidents[j] = icd;
          break;
        }
      }

      if (!find) {
        this.incidents.push(icd);
      }
    });

    // Remove all markers
    this.__deleteMarkers(map);

    // Create new markers
    this.incidents.forEach(icd => {
      this.markers.push({
        id: icd.id,
        lat: icd.location.latitude,
        long: icd.location.longitude,
        type: 'fire',
        popup: 'Intensité: ' + icd.intensity
      });
    });

    // Add markers to map
    this.__showMarkers(map);

  }

  /**
   * Delete markers
   */
  protected __deleteMarkers(map: any): void {
    this.markers.forEach(marker => {
      map.removeLayer(marker);
    });

    // Delete all markers from array
    this.markers = [];
  }

  /**
   * Show markers if they are defined
   */
  protected __showMarkers(map: any): void {

    if (this.markers !== undefined && this.markers != null && this.markers.length > 0) {

      const n: number = this.markers.length;
      let i: number;

      for (i = 0; i < n; ++i) {
        this.__addMarker(map, this.markers[i]);
      }
    }
  }

  /**
   * Add marker if defined
   */
  public __addMarker(map: any, marker: Marker): void {
    if (marker !== undefined && marker != null) {
      // Add marker
      const icon = L.icon({
        iconUrl: MapIconOptions.mapIcon[marker.type],
        iconSize: MapIconOptions.iconSize,
        iconAnchor: MapIconOptions.iconAnchor,
        popupAnchor: MapIconOptions.popupAnchor,
      });

      let m: L.marker;

      let x: number;
      let y: number;

      x = marker.lat;
      y = marker.long;

      if (x !== undefined && !isNaN(x) && y !== undefined && !isNaN(y)) {
        // okay to add
        m = L.marker([x, y], { icon: icon });
        if (marker.popup) {
          m.bindPopup(marker.popup);
        }
        m.addTo(map);
      }
      else {
        console.log('MARKER ERROR, x: ', x, ' y: ', y);
      }
    }
  }

}