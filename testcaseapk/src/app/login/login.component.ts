import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppComponent } from '../app.component';
import Swal from 'sweetalert2';
import { LoginService } from '../service/login.service';

declare var $:any;
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

username: any;
password: any;
isAdmin: any=false;

loginData = {
  email: '',
  password: ''
}
hidePassword = true;

  constructor(private login:LoginService, private router: Router, public obj:AppComponent) { }

  ngOnInit(): void {
  }
  
  backToLogin(){
    this.router.navigate([`./login`]);
  }

  formSubmit() {
    // Add the logic to validate and submit the data here
    if (this.loginData.email.trim()=='' || this.loginData.email.trim()==null) {
      Swal.fire({
        text: "Email is required !",
        showConfirmButton: false,
        timer: 600,
        position: 'top'
      });
      return;
    }
    if (this.loginData.password.trim()=='' || this.loginData.password.trim()==null) {
      Swal.fire({
        text: "Password is required !",
        showConfirmButton: false,
        timer: 600,
        position: 'top'
      });
      return;
    }

    //request to server to generate token
    this.login.generateToken(this.loginData).subscribe(
      (data:any) => {
        if (data.token && data.role == 'USER') {
          this.login.loginUser(data.token);
          this.login.setUser(data.username);
          this.login.setUserRole(data.role);
          this.router.navigate([`./exam/instructions`]);
          Swal.fire({
            text: "Welcome to Interland",
            showConfirmButton: false,
            timer: 600,
            position: 'top'
          });
        } else if (data.token && data.role == 'ADMIN') {
          this.login.loginUser(data.token);
          this.login.setUser(data.username);
          this.login.setUserRole(data.role);
          this.router.navigate([`./admin`]);
          Swal.fire({
            text: "Welcome to admin dasboard",
            showConfirmButton: false,
            timer: 600,
            position: 'top'
          });
        } else {
          Swal.fire({
            text: "An unexpected error occured !",
            showConfirmButton: false,
            timer: 600,
            position: 'top'
          });
        }
      },
      (error) => {
        Swal.fire({
          text: error.error+' !',
          showConfirmButton: false,
          timer: 600,
          position: 'top'
        });
      }
    )
  }

  togglePasswordVisibility() {
    this.hidePassword = !this.hidePassword;
  }

}
