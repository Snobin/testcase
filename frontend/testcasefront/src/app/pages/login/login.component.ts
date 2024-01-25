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

  constructor(private snack: MatSnackBar, private login: LoginService,private router:Router) { }


  ngOnInit(): void {
  }

  formSubmit() {
    // Add the logic to validate and submit the data here
    if (this.loginData.email.trim() == '' || this.loginData.email.trim() == null) {
      this.snack.open("Username is required !", '', {
        duration: 3000,
        verticalPosition: 'top'
      });
      return;
    }
    if (this.loginData.password.trim() == '' || this.loginData.password.trim() == null) {
      this.snack.open("Password is required !", '', {
        duration: 3000,
        verticalPosition: 'top'
      });
      return;
    }

    //request to server to generate token
    this.login.generateToken(this.loginData).subscribe(
      (data: any) => {
        console.log('success');
        console.log(data);
        this.login.loginUser(data.token);
        this.login.getCurrentUser().subscribe(
          (user: any) => {
            this.login.setUser(user);
            console.log(user);
            if (this.login.getUserRole() == "ADMIN") {
              // window.location.href = "/admin";
              this.router.navigate(['admin/profile']);
              this.login.loginStatusSubject.next(true);

            } else if (this.login.getUserRole() == "USER") {
              // window.location.href = "/user-dashboard";
              this.router.navigate(['instructions']);
              this.login.loginStatusSubject.next(true);
            }
            else {
              this.login.logout();
            }
          }
        );

      },
      (error) => {
        console.log('Error !');
        console.log(error);
        this.snack.open("Invalid Details....Try again","",{
          duration:2000,
        });
      }
    )
  }

  togglePasswordVisibility() {
    this.hidePassword = !this.hidePassword;
  }

  clear(){
    this.loginData.email = '';
    this.loginData.password = '';
  }


}
