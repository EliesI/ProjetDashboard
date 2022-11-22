import { Injectable } from '@angular/core';
import { catchError, Observable, retry, throwError } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AppConstants } from '../common/app.constants';
import { Widget } from './widget';
import { Weather } from './weather';

@Injectable({
  providedIn: 'root'
})
export class WidgetService {

  url = 'http://localhost:8080/widget';
  widget_url = 'http://localhost:8080/api/widgets/';
  delete_url= 'http://localhost:8080/widget/';

  constructor(private http: HttpClient) { }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  // Subcribe to Widget 

  createWidgetSubcr(widget: Widget): Observable<any> {
    return this.http.post(this.url, widget).pipe(catchError(this.handleError));
  }

  // Get widgets by company id

  getUserWidgets(user_id: string): Observable<Widget> {
    let api = `${this.url}/${user_id}`;
    return this.http
      .get<Widget>(api)
      .pipe(retry(1), catchError(this.handleError));
  }

  // Call weather service

  callWeatherWidget(city: string): Observable<any> {
    let api = `${this.widget_url}weather/${city}`;
    return this.http
      .get<any>(api)
      .pipe(retry(1), catchError(this.handleError));
  }

  // Call Twitter 1 service

  callTwitter1Widget(username: string): Observable<any> {
    let api = `${this.widget_url}twitter/${username}`;
    return this.http
      .get<any>(api)
      .pipe(retry(1), catchError(this.handleError));
  }

  // Call Twitter 2 service

  callTwitter2Widget(username: string): Observable<any> {
    let api = `${this.widget_url}twitter/gettweets/${username}`;
    return this.http
      .get<any>(api)
      .pipe(retry(1), catchError(this.handleError));
  }

  // Call Starwars 1 service

  callStarwars1Widget(planet: string): Observable<any> {
    let api = `${this.widget_url}starwars/planet/${planet}`;
    return this.http
      .get<any>(api)
      .pipe(retry(1), catchError(this.handleError));
  }

  // Call Starwars 2 service

  callStarwars2Widget(film: string): Observable<any> {
    let api = `${this.widget_url}starwars/film/${film}`;
    return this.http
      .get<any>(api)
      .pipe(retry(1), catchError(this.handleError));
  }

  // Call Starwars 3 service

  callStarwars3Widget(ship: string): Observable<any> {
    let api = `${this.widget_url}starwars/ship/${ship}`;
    return this.http
      .get<any>(api)
      .pipe(retry(1), catchError(this.handleError));
  }

  // Call News service

  callNewsWidget(country_code: string, zip_code: string): Observable<any> {
    let api = `${this.widget_url}infos/${country_code}/${zip_code}`;
    return this.http
      .get<any>(api)
      .pipe(retry(1), catchError(this.handleError));
  }

  // Call Fun 1 service

  callFun1Widget(city: string): Observable<any> {
    let api = `${this.widget_url}rickandmorty/${city}`;
    return this.http
      .get<any>(api)
      .pipe(retry(1), catchError(this.handleError));
  }

  // Call Fun 2 service

  callFun2Widget(name: string): Observable<any> {
    let api = `${this.widget_url}agify/${name}`;
    return this.http
      .get<any>(api)
      .pipe(retry(1), catchError(this.handleError));
  }


  // Delete Widget

  deleteWidget(widget_id: string): Observable<any> {
    let api = `${this.delete_url}${widget_id}`;
    return this.http.delete(api).pipe(catchError(this.handleError));
  }
  
  // Edit Widget

  editWidget(widget_id:string, widget: any): Observable<any> {
    let api = `${this.delete_url}${widget_id}`;
    return this.http.put(api, widget).pipe(catchError(this.handleError));
  }

  // Get Widget by ID

  getWidgetById(id: string): Observable<any> {
    let api = `${this.delete_url}getWidget/${id}`;
    return this.http
      .get<any>(api)
      .pipe(retry(1), catchError(this.handleError));
  }

  // Error handling
  handleError(error: any) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = error.error.message;
    } else {
      // Get server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(() => {
      return errorMessage;
    });
  }
}
