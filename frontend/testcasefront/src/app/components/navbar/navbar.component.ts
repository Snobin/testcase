import { AfterViewInit, Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';
import { UserService } from 'src/app/services/user.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit, OnDestroy {

  isLoggedIn = false;
  user = null;
  countdownMinutes: number = 10;
  countdownSeconds: number = 0;
  status: boolean = false;
  private statusSubscription: Subscription;

  constructor(public login: LoginService, private router: Router, private userService: UserService) { }



  ngOnInit(): void {
    this.isLogged();
    this.subscribeToStatus();
  }

  private subscribeToStatus(): void {
    // Ensure that the subscription is set up only once
    if (!this.statusSubscription) {
      this.statusSubscription = this.userService.status$.subscribe((status: boolean) => {
        this.status = status;
      });
    }
    // Start the countdown timer when the status becomes true
    if (this.status) {
      this.startCountdown();
    }
  }

  isLogged() {
    this.isLoggedIn = this.login.isloggedin();
    this.user = this.login.getUser();
    this.login.loginStatusSubject.asObservable().subscribe((data) => {
      this.isLoggedIn = this.login.isloggedin();
      this.user = this.login.getUser();
    });
  }

  logout() {
    this.login.logout();
    this.isLoggedIn = this.login.isloggedin();
    this.user = this.login.getUser();
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

  ngOnDestroy(): void {
    // Unsubscribe to avoid memory leaks
    if (this.statusSubscription) {
      this.statusSubscription.unsubscribe();
    }
  }
}
