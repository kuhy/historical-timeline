import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {handleError} from "../script/error";
import {catchError} from "rxjs/operators";

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
}
