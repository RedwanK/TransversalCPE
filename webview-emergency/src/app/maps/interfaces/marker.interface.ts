import * as L from 'leaflet';

export interface Marker {
    id: number,
    lat: number, 
    long: number,
    type: string, 
    popup: string,
    m: L.marker;
}