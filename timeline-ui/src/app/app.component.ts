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

  constructor(public router: Router, private userService: UserService) {}

  logout() {
    this.userService.logout().subscribe(data => {
      this.router.navigate(['/login']);
    });
  }
}
