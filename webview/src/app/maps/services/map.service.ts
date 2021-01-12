import { Injectable } from '@angular/core';
import { Marker } from '../interfaces/marker.interface';

import { MapIconOptions } from '../data/map-icon-options';

import * as L from 'leaflet';

@Injectable({
  providedIn: 'root',
})
export class MapService {

  constructor() { }

  /**
   * Delete markers
   */
  public __deleteMarkers(map: any, markers: Marker[]): void {
    markers.forEach(marker => {
      if (marker.m) {
        map.removeLayer(marker.m);
      }
    });

  }

  /**
   * Show markers if they are defined
   */
  public __showMarkers(map: any, markers: Marker[]): void {

    if (markers !== undefined && markers != null && markers.length > 0) {

      const n: number = markers.length;
      let i: number;

      for (i = 0; i < n; ++i) {
        this.__addMarker(map, markers[i]);
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
        marker.m = m;
      }
      else {
        console.log('MARKER ERROR, x: ', x, ' y: ', y);
      }
    }
  }

}