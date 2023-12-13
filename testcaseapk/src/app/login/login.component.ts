import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppComponent } from '../app.component';
import Swal from 'sweetalert2';

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

  login(){
    this.obj.isLoggedIn=true;
    this.router.navigate(['./exam/instructions']);
    Swal.fire({
      text: 'You are ready to go!',
      showConfirmButton: false,
      timer: 600,
      position: 'top'
    })
  }
  backToLogin(){
    this.router.navigate(['./login']);
  }
}
