/**
 * (Leaflet) Map Icon Options
 */

import { IconOptions } from '../interfaces/icon-options';

export const MapIconOptions: IconOptions =
{
  mapIcon: {
    location: './assets/maps/location.png',
    fire: './assets/maps/fire.gif',
    fireHuge: './assets/maps/fire-huge.gif',
    fireSmall: './assets/maps/fire-small.gif',
    fireTruck: './assets/maps/fire-truck.png'
  },
  iconSize: [38, 38],
  iconAnchor: [22, 37],
  popupAnchor: [-3, -20]
};
