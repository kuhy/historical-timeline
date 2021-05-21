import {Component, OnInit} from '@angular/core';
import {UserService} from "../../service/user.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  tmp: any;
  loginForm: FormGroup;
  loading = false;
  submitted = false;

  constructor(private formBuilder: FormBuilder, private router: Router, private userService: UserService) {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.userService.isUserLoggedIn().subscribe(response => {
      if (response) {
        // user is already logged in
        this.router.navigate(['/groups']);
      }
    })
  }

  // convenience getter for easy access to form fields
  get f() { return this.loginForm.controls; }

  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;
    this.userService.login(this.f.username.value, this.f.password.value).subscribe(
      data => {
        this.router.navigate(['/groups']);
      },
      error => {
        this.loading = false;
      });
  }

}
