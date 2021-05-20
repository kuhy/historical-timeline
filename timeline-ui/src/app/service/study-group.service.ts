import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, throwError} from "rxjs";
import {catchError} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class StudyGroupService {

  apiURL = 'http://localhost:8080/pa165/rest/groups';

  constructor(private http: HttpClient) { }

  getAllGroups(): Observable<any> {
    return this.http.get(`${this.apiURL}`).pipe(catchError(err => {
      return this.handleError(err)
    }));
  }

  handleError(error: any, customMessage = null): Observable<never> {
    if (customMessage != null) {
      window.alert(customMessage);
      return throwError(customMessage);
    }

    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = error.error.message;
    } else {
      // Get server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    window.alert(errorMessage);
    return throwError(errorMessage);
  }
}
