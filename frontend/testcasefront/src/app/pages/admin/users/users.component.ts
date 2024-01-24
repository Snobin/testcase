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

  loginPage: boolean = true;
  signupPage: boolean = false;
  constructor(private userservice:UserService,private snack:MatSnackBar,private renderer: Renderer2, private el: ElementRef) { }

  ngOnInit(): void {
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
