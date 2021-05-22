import { Component } from '@angular/core';
import {UserService} from "./service/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'timeline-ui';

  isTeacher = false

  constructor(public router: Router, private userService: UserService) {}

  ngOnInit(): void {
    this.getTeacherStatus()
  }

  private getTeacherStatus() {
    this.userService.isTeacher().subscribe(response => {
      this.isTeacher = response
    })
  }

  logout() {
    this.userService.logout().subscribe(data => {
      this.router.navigate(['/login']);
    });
  }

  studyGroups() {
    this.router.navigate(['/groups'])
  }

  users() {
    this.router.navigate(['/users'])
  }
}
