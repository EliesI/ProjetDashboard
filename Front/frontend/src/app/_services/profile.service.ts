import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import {
  HttpClient,
  HttpHeaders,
  HttpErrorResponse,
} from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {
  url: string = 'http://localhost:8080/';

  constructor(private http: HttpClient, public router: Router) { }

  // User edit
  editUser(id: any, user: any): Observable<any> {
    let api = `${this.url}api/${id}`;
    return this.http.put(api, user).pipe(catchError(this.handleError));
  }

  getUserProfile(id: any): Observable<any> {
    let api = `${this.url}api/getUser/${id}`;
    return this.http.get<any>(api).pipe(retry(1), catchError(this.handleError));
  }

  // Error
  handleError(error: HttpErrorResponse) {
    let msg = '';
    if (error.error instanceof ErrorEvent) {
      // client-side error
      msg = error.error.message;
    } else {
      // server-side error
      msg = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(msg);
  }
}
