import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  isLoggedIn=false;
  user=null;
  countdownMinutes: number = 10;
  countdownSeconds: number = 0;

  constructor(public login:LoginService, private router: Router) { }


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

  startCountdown(): void {
    const countdownInterval = setInterval(() => {
      if (this.countdownSeconds === 0) {
        if (this.countdownMinutes === 0) {
          clearInterval(countdownInterval);
          // Handle what happens when the countdown reaches 0
        } else {
          this.countdownMinutes--;
          this.countdownSeconds = 59;
        }
      } else {
        this.countdownSeconds--;
      }
    }, 1000);
  }

}
