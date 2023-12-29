import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginData = {
    email: '',
    password: ''
  }
  hidePassword = true;

  constructor(private snack:MatSnackBar, private login:LoginService, private router: Router) { }

  ngOnInit(): void {
  }

  formSubmit() {
    // Add the logic to validate and submit the data here
    if (this.loginData.email.trim()=='' || this.loginData.email.trim()==null) {
      this.snack.open("Email is required !",'',{
        duration:3000,
        verticalPosition:'top'
      });
      return;
    }
    if (this.loginData.password.trim()=='' || this.loginData.password.trim()==null) {
      this.snack.open("Password is required !",'',{
        duration:3000,
        verticalPosition:'top'
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
          this.router.navigate([`./home`]);
          this.snack.open("Welcome to Interland",'',{
            duration:3000,
            verticalPosition:'top'
          });
        } else if (data.token && data.role == 'ADMIN') {
          this.login.loginUser(data.token);
          this.login.setUser(data.username);
          this.login.setUserRole(data.role);
          this.router.navigate([`./admin`]);
          this.snack.open("Welcome to admin dasboard",'',{
            duration:3000,
            verticalPosition:'top'
          });
        } else {
          this.snack.open("An unexpected error occured !",'',{
            duration:3000,
            verticalPosition:'top'
          });
        }
      },
      (error) => {
        this.snack.open(error.error+' !','',{
          duration:3000,
          verticalPosition:'top'
        });
      }
    )
  }

  togglePasswordVisibility() {
    this.hidePassword = !this.hidePassword;
  }


}
