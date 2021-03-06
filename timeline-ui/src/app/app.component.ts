import { Component } from '@angular/core';
import {UserService} from "./service/user.service";
import {NavigationEnd, Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'timeline-ui';

  showMenu = false
  isTeacher = false

  constructor(public router: Router, private userService: UserService) {
    this.router.events.subscribe(response => {
      if (response instanceof NavigationEnd) {
        this.getShowMenu()
      }
    })
  }

  ngOnInit(): void {
    this.getTeacherStatus()
    this.getShowMenu()
  }

  private getTeacherStatus() {
    this.userService.isTeacher().subscribe(response => {
      this.isTeacher = response
    })
  }

  private getShowMenu() {
    this.userService.isUserLoggedIn().subscribe(response => {
      this.showMenu = response
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
