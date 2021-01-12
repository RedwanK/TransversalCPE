/**
 * Injection tokens for the Angular8/Leaflet Map
 */

import { InjectionToken } from '@angular/core';

export const INIT_COORDS = new InjectionToken<{lat: number, long: number}>('INIT_COORDS');
