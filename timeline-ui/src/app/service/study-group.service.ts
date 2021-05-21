import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {catchError} from "rxjs/operators";
import {handleError} from "../script/error";

@Injectable({
  providedIn: 'root'
})
export class StudyGroupService {

  apiURL = '/pa165/rest/groups';

  constructor(private http: HttpClient) { }

  getAllGroups(): Observable<any> {
    return this.http.get(`${this.apiURL}`).pipe(catchError(err => {
      return handleError(err)
    }));
  }
}
