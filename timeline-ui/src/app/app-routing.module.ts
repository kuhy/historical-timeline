import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {StudyGroupComponent} from "./component/study-group/study-group.component";
import {LoginComponent} from "./component/login/login.component";
import {UserComponent} from "./component/user/user.component";
import {HistoricalTimelineComponent} from "./component/historical-timeline/historical-timeline.component";
import {AddUserToGroupComponent} from "./component/add-user-to-group/add-user-to-group.component";
import {HistoricalEventComponent} from "./component/historical-event/historical-event.component";
import {TimelineCommentComponent} from "./component/timeline-comment/timeline-comment.component";

const routes: Routes = [
  {path: '', redirectTo: '/login', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'groups', component: StudyGroupComponent},
  {path: 'users', component: UserComponent},
  {path: 'groups/:studyGroupId', component: HistoricalTimelineComponent},
  {path: 'groups/add_user/:studyGroupId', component: AddUserToGroupComponent},
  {path: 'groups/:studyGroupId/timeline/events/:timelineId', component: HistoricalEventComponent},
  {path: 'groups/:studyGroupId/timeline/comments/:timelineId', component: TimelineCommentComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
