import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';
import { MapPageOptions } from '../data/map-page-options';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  private REST_API_SERVER = MapPageOptions.env;

  public PAGE = null;

  public PARAMS = null;

  constructor(private httpClient: HttpClient) { }

  handleError(error: HttpErrorResponse) {
    let errorMessage = 'Unknown error!';
    if (error.error instanceof ErrorEvent) {
      // Client-side errors
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Server-side errors
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(errorMessage);
  }

  public sendGetRequest(){
    if (this.PAGE){
      if(this.PARAMS){
        return this.httpClient.post(this.REST_API_SERVER + this.PAGE, JSON.stringify(this.PARAMS)).pipe(retry(3), catchError(this.handleError));
      }
      return this.httpClient.get(this.REST_API_SERVER + this.PAGE).pipe(retry(3), catchError(this.handleError));
    }
  }
}
