import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute, NavigationEnd } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';
import { UserService } from 'src/app/services/user.service';
import { Subscription } from 'rxjs';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit, OnDestroy {

  isLoggedIn = false;
  user = null;
  countdownMinutes: number = 1;
  countdownSeconds: number = 0;
  status: boolean;
  private statusSubscription: Subscription;
  private routeSubscription: Subscription;
   showTimer: boolean = false;

  constructor(
    public login: LoginService,
    private router: Router,
    private route: ActivatedRoute,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.isLogged();
    this.subscribeToStatus();

    // Subscribe to route changes to reinitialize the timer
    this.routeSubscription = this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe(() => {
        this.handleRouteChange();
      });
  }

  private handleRouteChange(): void {
    // Reset the timer and status when navigating to specific components
    console.log(this.isTimerVisible(this.router.url));
    
    if (this.isTimerVisible(this.router.url)) {
      this.countdownMinutes = 1;
      this.countdownSeconds = 0;
      this.status = false;
      this.subscribeToStatus(); // Subscribe again since ngOnDestroy might have unsubscribed
  
      // Check if the timer should be visible based on the current route
      console.log(this.router.url);
      this.showTimer = true; // Set to true when the route is eligible for the timer
      console.log(this.showTimer);
    } else {
      this.showTimer = false; // Set to false when the route is not eligible for the timer
    }
  }
  

  private subscribeToStatus(): void {
    // Ensure that the subscription is set up only once
    if (!this.statusSubscription) {
      this.statusSubscription = this.userService.status$.subscribe((status: boolean) => {
        this.status = status;

        // Start the countdown timer when the status becomes true
        if (this.status) {
          this.startCountdown();
        }
      });
    }
  }

   isTimerVisible(url: string): boolean {
    // Add logic to check if the timer should be visible for specific routes/components
    return (
      url.includes('/user-dashboard') ||
      url.includes('/coding') ||
      url.includes('/start')
      // Add more conditions as needed for other components
    );
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
      this.countdownSeconds--;

      if (this.countdownSeconds < 0) {
        this.countdownMinutes--;

        this.countdownSeconds = 59;
      }

      if (this.countdownMinutes === 0 && this.countdownSeconds === 0) {
        clearInterval(countdownInterval);
        this.router.navigate(['/final']);
        this.status = false;
      }
    }, 1000);
  }

  ngOnDestroy(): void {
    // Unsubscribe to avoid memory leaks
    if (this.statusSubscription) {
      this.statusSubscription.unsubscribe();
    }

    // Unsubscribe from route changes
    if (this.routeSubscription) {
      this.routeSubscription.unsubscribe();
    }
  }
}
