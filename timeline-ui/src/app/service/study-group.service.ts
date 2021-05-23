import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {catchError} from "rxjs/operators";
import {handleError} from "../script/error";
import {StudyGroupDTO} from "../dto/study-group-dto";
import {HistoricalTimelineCreateDTO} from "../dto/historical-timeline-create-dto";
import {StudyGroupCreateDTO} from "../dto/study-group-create-dto";

@Injectable({
  providedIn: 'root'
})
export class StudyGroupService {

  apiURL = '/pa165/rest/groups';

  constructor(private http: HttpClient) { }

  getAllGroups(): Observable<any> {
    return this.http.get(`${this.apiURL}`).pipe(catchError(error => {
      return handleError(error)
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

  createGroup(createGroupDTO: StudyGroupCreateDTO): Observable<any> {
    return this.http.post(`${this.apiURL}/create`, createGroupDTO).pipe(catchError(error => {
      return handleError(error)
    }));
  }

  getGroup(id: number): Observable<any> {
    return this.http.get(`${this.apiURL}/${id}`).pipe(catchError(error => {
      return handleError(error)
    }));
  }

  addHistoricalTimeline(studyGroupId: number, createHistoricalTimelineDTO: HistoricalTimelineCreateDTO): Observable<any> {
    return this.http.post(`${this.apiURL}/${studyGroupId}/timelines/create`, createHistoricalTimelineDTO).pipe(catchError(error => {
      return handleError(error)
    }));
  }

  addUserToStudyGroup(studyGroupId: number, userId: number) {
    // TODO studyGroupId should not be passed in put ?
    return this.http.put(`${this.apiURL}/${studyGroupId}/users/${userId}`, studyGroupId).pipe(catchError(error => {
      return handleError(error)
    }));
  }

  removeUserFromStudyGroup(studyGroupId: number, userId: number) {
    return this.http.delete(`${this.apiURL}/${studyGroupId}/users/${userId}`).pipe(catchError(error => {
      return handleError(error)
    }));
  }
}
