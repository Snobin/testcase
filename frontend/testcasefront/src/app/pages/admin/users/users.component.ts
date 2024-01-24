import { Component, ElementRef, OnInit, Renderer2 } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { User } from 'src/app/model/user';
import { UserService } from 'src/app/services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
quizdata: any;
handleFileInput($event: any) {
throw new Error('Method not implemented.');
}
formSubmit() {
throw new Error('Method not implemented.');
}

  loginPage: boolean = true;
  signupPage: boolean = false;
  userdata: User=new User();
  constructor(private userservice:UserService,private snack:MatSnackBar,private renderer: Renderer2, private el: ElementRef) { }

  ngOnInit(): void {
  }

  toggleUserRole() {
    // If the toggle is turned on, set role to "admin"; otherwise, set it to "user"
    this.userdata.role = this.userdata.role === 'admin' ? 'USER' : 'ADMIN';
  }
  
  
  openLogin() {
    this.loginPage = true;
    this.signupPage = false;
  }

  openSignup() {
    this.loginPage = false;
    this.signupPage = true;
  }
}
