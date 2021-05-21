import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {StudyGroupComponent} from "./component/study-group/study-group.component";
import {LoginComponent} from "./component/login/login.component";

const routes: Routes = [
  {path: '', redirectTo: '/login', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'groups', component: StudyGroupComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
