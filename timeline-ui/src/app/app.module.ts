import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {StudyGroupComponent} from './component/study-group/study-group.component';
import {HttpClientModule} from "@angular/common/http";
import {ReactiveFormsModule} from "@angular/forms";
import { LoginComponent } from './component/login/login.component';
import { UserComponent } from './component/user/user.component';
import { HistoricalTimelineComponent } from './component/historical-timeline/historical-timeline.component';
import { HistoricalEventComponent } from './component/historical-event/historical-event.component';
import { AddUserToGroupComponent } from './component/add-user-to-group/add-user-to-group.component';
import { TimelineCommentComponent } from './component/timeline-comment/timeline-comment.component';

@NgModule({
  declarations: [
    AppComponent,
    StudyGroupComponent,
    LoginComponent,
    UserComponent,
    HistoricalTimelineComponent,
    HistoricalEventComponent,
    AddUserToGroupComponent,
    TimelineCommentComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
