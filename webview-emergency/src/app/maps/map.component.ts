import {
  AfterViewInit,
  Component,
  ElementRef,
  Inject,
  Input,
  HostListener,
  OnDestroy,
  OnInit,
  ViewChild
} from '@angular/core';

import { getCurrentOffset } from './libs/map-libs';

import { EventHandler } from './interfaces/event-handler';
import { INIT_COORDS } from '../tokens';

import * as esri from 'esri-leaflet';
import * as L from 'leaflet';
import { LocationService } from './services/location.service';
import { Marker } from './interfaces/marker.interface';
import { interval, Subject, Subscription } from 'rxjs';
import { DataService } from './services/data.service';
import { Incident } from './interfaces/incident.interface';
import { takeUntil } from 'rxjs/operators';
import { ApiService } from './services/api.service';
import { MapService } from './services/map.service';
import { Intervention } from './interfaces/intervention.interface';
import { Sensor } from './interfaces/sensor.interface';
import { MapPageOptions } from './data/map-page-options';

/**
 * Leaflet Map Component
 */
@Component({
  selector: 'app-map',

  templateUrl: './map.component.html',

  styleUrls: ['./map.component.scss'],

  providers: [DataService]
})
export class MapComponent implements OnInit, AfterViewInit, OnDestroy {

  public mcText: string;                         // mouse coords text (innerHTML)

  public currentWidth: number;                   // current map width based on window width
  public currentHeight: number;                  // current map height based on window height

  protected baseLayer: any;                      // Map Base layer
  protected map: any;                            // Map reference (currently leaflet)
  protected mapLoaded = false;                   // True if the map has been loaded

  // Destroy pending request when leave 
  protected destroy$: Subject<boolean> = new Subject<boolean>(); 

  protected interventions: Intervention[];
  protected incidents: Incident[];
  protected sensors: Sensor[];

  // The primary Map
  @ViewChild('primaryMap', { static: true }) protected mapDivRef: ElementRef;
  protected mapDiv: HTMLDivElement;

  // Leaflet Map Event Handlers (used for removal on destroy)
  protected onClickHandler: EventHandler;
  protected onMouseMoveHandler: EventHandler;

  protected subscription: Subscription;
  
  constructor(
    @Inject(INIT_COORDS) protected _initCoords: { lat: number, long: number }, 
    private locationService: LocationService,
    private dataService: DataService,
    private apiService: ApiService,
    private mapService: MapService
  ) {
    this.baseLayer = null;

    // Leaflet Map Event Handlers
    this.onClickHandler = (evt: any) => this.__onMapClick(evt);
    this.onMouseMoveHandler = (evt: any) => this.__onMapMouseMove(evt);

    // Initial mouse-coords text
    this.mcText = '';

    // some simple default values
    this.currentWidth = 600;
    this.currentHeight = 200;

  }

  public ngOnInit(): void {
    // Reference to DIV containing map is used in Leaflet initialization
    this.mapDiv = this.mapDivRef.nativeElement;

    this.__initializeMap();
    this.__renderMap();
    this.__updateFromApi()

    // Subscribe to api
    const source = interval(10000);
    this.subscription = source.subscribe(val => this.__updateFromApi());

    // Get current location
    this.locationService.getPosition().then(pos=>
    {
      this._initCoords.lat = pos.lat;
      this._initCoords.long = pos.lng;

      let m: Marker = { 
        id: 1, 
        lat: pos.lat, 
        long: pos.lng,
        type: 'location',
        popup: 'Vous êtes ici en somme.',
        m: null
      };

      this.mapService.__addMarker(this.map, m);
      this.map.setView([this._initCoords.lat, this._initCoords.long], 10);
    });
  }

  public ngAfterViewInit(): void {
    this.map.invalidateSize();

    this.__initMapHandlers();
  }

  public ngOnDestroy(): void {
    this.map.off('click', this.onClickHandler);
    this.map.off('mousemove', this.onMouseMoveHandler);

    this.destroy$.next(true);
    this.destroy$.unsubscribe();

    this.subscription.unsubscribe();
  }

  /**
   * Basic map initialization
   */
  protected __initializeMap(): void {
    if (this.mapLoaded) {
      return;
    }

    this.mapLoaded = true;

    this.__updateMapSize();
  }

  /**
   * Render the map (establish center and base layer)
   */
  protected __renderMap(): void {
    // Create Leaflet Map in fixed DIV - zoom level is hardcoded for simplicity
    this.map = L.map(this.mapDiv, {
      zoomControl: true,
      zoomAnimation: false,
      trackResize: true,
      boxZoom: true,
    }).setView([this._initCoords.lat, this._initCoords.long], 10);

    this.baseLayer = esri.basemapLayer('DarkGray');
    this.map.addLayer(this.baseLayer);
  }

  /**
   * Update from API
   */
  protected __updateFromApi(): void {
    /* Get interventions */
    this.dataService.PAGE = MapPageOptions.intervention;
    this.dataService.sendGetRequest().pipe(takeUntil(this.destroy$)).subscribe((data: any[]) => {
      this.interventions = data as Intervention[];

      // Update interventions markers
      this.apiService.__updateInterventions(this.map, this.interventions)

    });

    /* Get incidents */
    this.dataService.PAGE = MapPageOptions.incident;
    this.dataService.sendGetRequest().pipe(takeUntil(this.destroy$)).subscribe((data: any[]) => {
      this.incidents = data as Incident[];

      // Update incidents markers
      this.apiService.__updateIncidents(this.map, this.incidents)

    });

    /* Get sensors 
    this.dataService.PAGE = MapPageOptions.sensor;
    this.dataService.sendGetRequest().pipe(takeUntil(this.destroy$)).subscribe((data: any[]) => {
      this.sensors = data as Sensor[];

      // Update interventions markers
      this.apiService.__updateSensors(this.map, this.sensors)

    }); */
  }

  @HostListener('window:resize', ['$event'])
  protected __onResize(event: any): void {
    this.__updateMapSize();

    this.map.invalidateSize();
  }

  /**
   * Update the current width/height occupied by the map
   */
  protected __updateMapSize(): void {
    // update width/height settings as you see fit
    this.currentWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
    this.currentHeight = (window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight) - 120;
  }

  /**
   * Initialize Leaflet Map handlers
   */
  protected __initMapHandlers(): void {
    this.map.on('mousemove', this.onMouseMoveHandler);
    this.map.on('click', this.onClickHandler);
  }

  /**
   * Execute on Leaflet Map click
   */
  protected __onMapClick(evt: any): void {

    const target: any = evt.originalEvent.target;

    //console.log('Map click on: ', target);
  }

  /**
   * Execute on mouse move over Leaflet map
   *
   * @param evt Leaflet-supplied information regarding current mouse point, mainly geo coords.
   */
  protected __onMapMouseMove(evt: any): void {
    const offset: { x: number, y: number } = getCurrentOffset(this.map);

    // uncomment to study offset
    // console.log('offset computation:', offset);

    // Lat and Long are embedded in the event object
    const lat: string = evt.latlng.lat.toFixed(3);
    const long: string = evt.latlng.lng.toFixed(3);
    this.mcText = `Latitude: ${lat} &nbsp; &nbsp; Longitude: ${long}`;
  }
}
