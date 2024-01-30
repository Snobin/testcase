import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { User } from 'src/app/model/user';
import { LoginService } from 'src/app/services/login.service';
import { UserService } from 'src/app/services/user.service';
import Swal from 'sweetalert2';
declare var $: any;

@Component({
  selector: 'app-adminupdate',
  templateUrl: './adminupdate.component.html',
  styleUrls: ['./adminupdate.component.css']
})
export class AdminupdateComponent implements OnInit {


  userdata: User=new User();
  
  user: any;

  constructor(private userservice:UserService,private snack:MatSnackBar, private router:Router,private login:LoginService) { }

  ngOnInit(): void {
    this.login.getCurrentUser().subscribe(
      (user:any) => {
        console.log(user);
        this.userdata=user;
      },
      (error) => {
        alert("error")
      }
    )
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
        if (data.body == 'Successfully Inserted') {
          this.router.navigate([`./login`]);
          Swal.fire("Success",'User is Registered','success')
        } else {
          Swal.fire('Error!', data.body,'error');
        }
      },
      (error)=>{
        console.log(error);
        alert("error");
      }
    )
   

  }
  update(){
    this.userservice.update(this.userdata).subscribe(
      (data)=>{
        if (data.body == 'Successfully Inserted') {
          this.router.navigate([`./admin`]);
          Swal.fire("Success",'User is Registered','success')
        } else {
          Swal.fire('Error!', data.body,'error');
        }
      },
      (error)=>{
        console.log(error);
        alert("error");
      })


  }

}
