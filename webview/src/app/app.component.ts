import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  public markers: { lat: number, long: number }[];   // Map markers (relevance depends on map center)

  constructor() {
    // some map markers
    this.markers = [
      { lat: 45.764043, long: 4.835659 },
    ];
  }
}
