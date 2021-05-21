import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {StudyGroupComponent} from "./component/study-group/study-group.component";
import {LoginComponent} from "./component/login/login.component";
import {HistoricalTimelineComponent} from "./component/historical-timeline/historical-timeline.component";

const routes: Routes = [
  {path: '', redirectTo: '/login', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'groups', component: StudyGroupComponent},
  {path: 'groups/:id', component: HistoricalTimelineComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
