import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { User } from 'src/app/model/user';
import { UserService } from 'src/app/services/user.service';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  userdata: User = new User();
  validationMessage: any;

  constructor(private userservice: UserService, private snack: MatSnackBar, private router: Router) { }

  ngOnInit(): void {
  }

  formSubmit() {
    console.log(this.userdata);
    if (this.userdata.username == null || this.userdata.username == "") {
      this.snack.open("username is required ...", '', {
        duration: 3000,
        verticalPosition: 'top'
      });
      return;
    }
    this.userservice.addUser(this.userdata).subscribe(
      (data:any) => {
        if (data.body == 'Successfully Inserted') {
          this.router.navigate([`./login`]);
          Swal.fire("Success", 'User is Registered', 'success')
        } else {
          if (data.details) {
            data.details.forEach((element) => {
              var key = Object.keys(element)[0];
              this.validationMessage[key] = element[key];
            });
          // console.log(this.validationMessage);
          
          }
          // Swal.fire('Error!', data["message"], 'error');
        }
        
        console.log(data);
      },
      error => {

        console.log(error);
        // alert("error");
      }, () => {
      })
  }
}
