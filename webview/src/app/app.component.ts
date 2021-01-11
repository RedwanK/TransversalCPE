import { Component } from '@angular/core';
import { Marker } from './models/marker.interface';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  public markers: Marker[];   // Map markers (relevance depends on map center)

  constructor() {
    // some map markers
    this.markers = [
      { id: 1, lat: 45.764043, long: 4.835659 },
    ];
  }
}
