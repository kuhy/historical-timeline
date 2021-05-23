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
import { TimelineCommentComponent } from './component/timeline-comment/timeline-comment.component';
import { StudyGroupUsersComponent } from './component/manage-study-group-users/study-group-users.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@NgModule({
  declarations: [
    AppComponent,
    StudyGroupComponent,
    LoginComponent,
    UserComponent,
    HistoricalTimelineComponent,
    HistoricalEventComponent,
    TimelineCommentComponent,
    StudyGroupUsersComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FontAwesomeModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
