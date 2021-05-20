import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {StudyGroupComponent} from "./component/study-group/study-group.component";

const routes: Routes = [
  {path: '', redirectTo: '/groups', pathMatch: 'full'},
  {path: 'groups', component: StudyGroupComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
