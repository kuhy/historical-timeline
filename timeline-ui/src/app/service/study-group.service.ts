import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {catchError} from "rxjs/operators";
import {handleError} from "../script/error";
import {StudyGroupDTO} from "../dto/study-group-dto";

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

  deleteGroup(id: number) {
    return this.http.delete(`${this.apiURL}/${id}`).pipe(catchError(error => {
      return handleError(error)
    }));
  }

  updateGroup(updateGroupDTO: StudyGroupDTO): Observable<any> {
    return this.http.put(`${this.apiURL}/${updateGroupDTO.id}`, updateGroupDTO).pipe(catchError(error => {
      return handleError(error)
    }));
  }

  createGroup(createGroupDTO: StudyGroupDTO): Observable<any> {
    return this.http.post(`${this.apiURL}/create`, createGroupDTO).pipe(catchError(error => {
      return handleError(error)
    }));
  }
}
