import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoginService } from 'src/app/services/login.service';
import { error, log } from 'console';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginData = {
    username: '',
    password: ''
  }

  constructor(private snack:MatSnackBar, private login:LoginService) { }

  ngOnInit(): void {
  }

  formSubmit() {
    console.log('Form Submitted!');
    // Add the logic to validate and submit the data here
    if (this.loginData.username.trim()=='' || this.loginData.username.trim()==null) {
      this.snack.open("Username is required !",'',{
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
        console.log('success');
        console.log(data);
      },
      (error) => {
        console.log('Error !');
        console.log(error);
      }
    )
  }

}
