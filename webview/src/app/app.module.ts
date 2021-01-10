import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { MapComponent } from './maps/map.component';

import { INIT_COORDS } from './tokens';

@NgModule({
  declarations: [
    AppComponent,
    MapComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [{ provide: INIT_COORDS, useValue: { lat: 45.764043, long: 4.835659 } }], // Default Lyon
  bootstrap: [AppComponent]
})
export class AppModule { }
