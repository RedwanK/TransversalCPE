/**
 * (Leaflet) Map Icon Options
 */

import { IconOptions } from '../interfaces/icon-options';
import { MapTypeOptions } from './map-type-options';

export const MapIconOptions: IconOptions =
{
  mapIcon: {
    location: MapTypeOptions.location,
    fire: MapTypeOptions.fire,
    fireHuge: MapTypeOptions.fireHuge,
    fireSmall: MapTypeOptions.fireSmall,
    fireTruck: MapTypeOptions.fireTruck,
    sensor: MapTypeOptions.sensor
  },
  iconSize: [38, 38],
  iconAnchor: [22, 37],
  popupAnchor: [-3, -20]
};
