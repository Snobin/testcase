import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { User } from 'src/app/model/user';
import { UserService } from 'src/app/services/user.service';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  userdata: User=new User();

  constructor(private userservice:UserService,private snack:MatSnackBar) { }

  ngOnInit(): void {
  }

  formSubmit() {
    console.log(this.userdata);
    if(this.userdata.username==null||this.userdata.username==""){
      this.snack.open("username is required ...",'',{
        duration:3000,
        verticalPosition:'top'
      });
      return;
    }
    this.userservice.addUser(this.userdata).subscribe(
      (data)=>{
        console.log(data);
        Swal.fire("Success",'User is Registered','success')
      },
      (error)=>{
        console.log(error);
        alert("error");
      }
    )
  

  }
}
