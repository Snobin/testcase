import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  isLoggedIn=false;
  user=null;

  constructor(public login:LoginService, private router: Router, public service:UserService) { }


  ngOnInit(): void {
    this.isLogged();
  }

  isLogged(){
    this.isLoggedIn=this.login.isloggedin();
    this.user=this.login.getUser();
    this.login.loginStatusSubject.asObservable().subscribe((data) => {
      this.isLoggedIn=this.login.isloggedin();
    this.user=this.login.getUser();
    });
  }
  
  logout(){
    this.login.logout();
    this.isLoggedIn=this.login.isloggedin();
    this.user=this.login.getUser();
    this.router.navigate([`login`]);
  }

  timeStatus(){
    return this.service.timeShow();
  }

}
