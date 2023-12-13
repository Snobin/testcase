import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppComponent } from '../app.component';

declare var $:any;
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

username: any;
password: any;

  constructor(private router: Router, public obj:AppComponent) { }

  ngOnInit(): void {
  }

  Login(){
    this.obj.isLoggedIn=true;
    this.router.navigate(['./exam/instructions']);
  }
  login(){
    this.router.navigate(['./login']);
  }
}
