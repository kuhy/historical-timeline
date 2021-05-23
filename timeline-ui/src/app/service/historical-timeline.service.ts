import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {catchError} from "rxjs/operators";
import {handleError} from "../script/error";
import {HistoricalTimelineDTO} from "../dto/historical-timeline-dto";
import {HistoricalTimelineCreateDTO} from "../dto/historical-timeline-create-dto";
import {HistoricalEventCreateDTO} from "../dto/historical-event-create-dto";
import {TimelineCommentCreateDTO} from "../dto/timeline-comment-create-dto";
import {observableToBeFn} from "rxjs/internal/testing/TestScheduler";

@Injectable({
  providedIn: 'root'
})
export class HistoricalTimelineService {

  apiURL = '/pa165/rest/timelines'

  constructor(private http: HttpClient) { }

  getAllHistoricalTimelines(): Observable<any> {
    return this.http.get(`${this.apiURL}`).pipe(catchError(err => {
      return handleError(err)
    }));
  }

  deleteHistoricalTimeline(id: number) {
    return this.http.delete(`${this.apiURL}/${id}`).pipe(catchError(err => {
      return handleError(err)
    }));
  }

  updateHistoricalTimeline(historicalTimelineDTO: HistoricalTimelineDTO): Observable<any> {
    return this.http.put(`${this.apiURL}/${historicalTimelineDTO.id}`, historicalTimelineDTO).pipe(catchError(err => {
      return handleError(err)
    }));
  }

  getHistoricalTimeline(id: number): Observable<any> {
    return this.http.get(`${this.apiURL}/${id}`).pipe(catchError(err => {
      return handleError(err)
    }));
  }

  addEventToTimeline(timelineId: number, createEventDTO: HistoricalEventCreateDTO): Observable<any> {
    return this.http.post(`${this.apiURL}/${timelineId}/events/create`, createEventDTO).pipe(catchError(error => {
      return handleError(error)
    }));
  }

  addCommentInTimeline(timelineId: number, timelineCommentCreateDTO: TimelineCommentCreateDTO): Observable<any> {
    return this.http.post(`${this.apiURL}/${timelineId}/comments/create`, timelineCommentCreateDTO).pipe(catchError(err => {
      return handleError(err)
    }));
  }

  addHistoricalTimeline(studyGroupId: number, createHistoricalTimelineDTO: HistoricalTimelineCreateDTO): Observable<any> {
    return this.http.post(`${this.apiURL}/${studyGroupId}/timelines/create`, createHistoricalTimelineDTO).pipe(catchError(error => {
      return handleError(error)
    }));
  }
}
