import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit {

  loginPage: boolean = true;
  signupPage: boolean = false;
  constructor() { }

  ngOnInit(): void {
  }

  openLogin() {
    this.loginPage = true;
    this.signupPage = false;
  }

  openSignup() {
    this.loginPage = false;
    this.signupPage = true;
  }
}
