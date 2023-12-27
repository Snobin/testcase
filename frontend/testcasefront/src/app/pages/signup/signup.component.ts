import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/model/user';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  userdata: User=new User();

  constructor(private userservice:UserService) { }

  ngOnInit(): void {
  }

  formSubmit() {
    console.log(this.userdata);
    this.userservice.addUser(this.userdata).subscribe(
      (data)=>{
        console.log(data);
        alert("Success");
      },
      (error)=>{
        console.log(error);
        alert("error");
      }
    )
  }
}
