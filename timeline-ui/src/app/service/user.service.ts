import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {handleError} from "../script/error";
import {catchError} from "rxjs/operators";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  apiURL = `/pa165/rest/users`;

  constructor(private http: HttpClient) { }

  login(username: string, password: string) {
    return this.http.get(`${this.apiURL}/login/${username}/${password}`).pipe(catchError(error => {
      return handleError(error,"Login unsuccessful!");
    }));
  }

  getLoggedInUser(): Observable<any> {
    return this.http.get(`${this.apiURL}/logged_in_user`).pipe(catchError(error => {
      return handleError(error);
    }));
  }

  isTeacher(): Observable<any> {
    return this.http.get(`${this.apiURL}/is_teacher`).pipe(catchError(error => {
      return handleError(error)
    }))
  }
}
