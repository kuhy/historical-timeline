import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {catchError} from "rxjs/operators";
import {handleError} from "../script/error";
import {TimelineCommentDTO} from "../dto/timeline-comment-dto";

@Injectable({
  providedIn: 'root'
})
export class TimelineCommentService {

  apiURL = '/pa165/rest/comments'

  constructor(private http: HttpClient) { }

  getAllTimelineComments(): Observable<any> {
    return this.http.get(`${this.apiURL}`).pipe(catchError(err => {
      return handleError(err)
    }));
  }

  deleteTimelineComment(id: number) {
    return this.http.delete(`${this.apiURL}/${id}`).pipe(catchError(err => {
      return handleError(err)
    }));
  }

  updateTimelineComment(timelineCommentDTO: TimelineCommentDTO): Observable<any> {
    return this.http.put(`${this.apiURL}/${timelineCommentDTO.id}`, timelineCommentDTO).pipe(catchError(err => {
      return handleError(err)
    }));
  }

  getTimelineComment(id: number): Observable<any> {
    return this.http.get(`${this.apiURL}/${id}`).pipe(catchError(err => {
      return handleError(err)
    }));
  }
}
