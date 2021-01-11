/**
 * (Leaflet) Map Icon Options
 */

import { IconOptions } from '../interfaces/icon-options';

export const MapIconOptions: IconOptions =
{
  mapIcon: {
    location: './assets/maps/location.png',
    fire: './assets/maps/fire.png',
    fireExtinguisher: './assets/maps/fire-extinguisher.png',
    fireTruck: './assets/maps/fire-truck.png'
  },
  iconSize: [38, 38],
  iconAnchor: [22, 37],
  popupAnchor: [-3, -20]
};
