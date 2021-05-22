import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {HistoricalEventDTO} from "../dto/historical-event-dto";
import {catchError} from "rxjs/operators";
import {handleError} from "../script/error";
import {HistoricalTimelineCreateDTO} from "../dto/historical-timeline-create-dto";
import {HistoricalEventCreateDTO} from "../dto/historical-event-create-dto";

@Injectable({
  providedIn: 'root'
})
export class HistoricalEventService {

  apiURL = '/pa165/rest/events'

  constructor(private http: HttpClient) { }

  getAllHistoricalEvents(): Observable<any> {
    return this.http.get(`${this.apiURL}`).pipe(catchError(err => {
      return handleError(err)
    }));
  }

  deleteHistoricalEvent(id: number) {
    return this.http.delete(`${this.apiURL}/${id}`).pipe(catchError(err => {
      return handleError(err)
    }));
  }

  updateHistoricalEvent(historicalEventDTO: HistoricalEventDTO): Observable<any> {
    return this.http.put(`${this.apiURL}/${historicalEventDTO.id}`,  historicalEventDTO).pipe(catchError(err => {
      return handleError(err)
    }));
  }

  getHistoricalTimeline(id: number): Observable<any> {
    return this.http.get(`${this.apiURL}/${id}`).pipe(catchError(err => {
      return handleError(err)
    }));
  }
}
