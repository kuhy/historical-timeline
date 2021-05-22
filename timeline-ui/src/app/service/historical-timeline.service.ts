import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {catchError} from "rxjs/operators";
import {handleError} from "../script/error";
import {HistoricalTimelineDTO} from "../dto/historical-timeline-dto";
import {HistoricalTimelineCreateDTO} from "../dto/historical-timeline-create-dto";
import {HistoricalEventCreateDTO} from "../dto/historical-event-create-dto";

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
    return this.http.post(`${this.apiURL}/${timelineId}/timelines/create`, createEventDTO).pipe(catchError(error => {
      return handleError(error)
    }));
  }
}