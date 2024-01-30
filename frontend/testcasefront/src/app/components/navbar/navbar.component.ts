import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute, NavigationEnd } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';
import { UserService } from 'src/app/services/user.service';
import { Subscription } from 'rxjs';
import { filter } from 'rxjs/operators';
import { CategoryService } from 'src/app/services/category.service';
import { MatSnackBar } from '@angular/material/snack-bar';


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
  status: boolean = false;
  private statusSubscription: Subscription;
  private routeSubscription: Subscription;
  showTimer: boolean = false;
  categories: any;
  private countdownInterval: any;
  showdata: boolean;
  logoutdata: boolean;
  private storageKey = 'timerState'; // Key for storing timer state in sessionStorage

  constructor(
    public login: LoginService,
    private router: Router,
    private route: ActivatedRoute,
    private userService: UserService,
    private cat: CategoryService,
    private snack: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.isLogged();
    this.subscribeToStatus();

    // Subscribe to route changes to reinitialize the timer
    this.routeSubscription = this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe(() => {
        this.handleRouteChange();
      });

    // Initialize the timer based on stored state
    const storedState = sessionStorage.getItem(this.storageKey);
    if (storedState) {
      const { countdownMinutes, countdownSeconds, status } = JSON.parse(storedState);
      this.countdownMinutes = countdownMinutes;
      this.countdownSeconds = countdownSeconds;
      this.status = status;

      // Resume the countdown if the status is true
      if (status) {
        this.startCountdown();
      }
    }
  }

  getCategories() {
    this.cat.categories().subscribe((data: any) => {
      this.categories = data;
      console.log(this.categories);

      this.router.navigate([`./user-dashboard/${this.categories[0].title}/${this.categories[0].cid}`]);
    },
      (error) => {
        this.snack.open('Error in loading categories from server', '', {
          duration: 3000,
        });
      }
    );
  }

  private handleRouteChange(): void {
    // Update the showdata based on the current URL
    this.showdata = this.show(this.router.url);
    this.logoutdata = this.logouthere(this.router.url)
    console.log(this.showdata);
    this.unsubscribeFromStatus();

    // Reset the timer and status when navigating to specific components    
    if (this.isTimerVisible(this.router.url)) {
      // this.countdownMinutes = 1;
      // this.countdownSeconds = 0;
      this.subscribeToStatus(); // Subscribe again since ngOnDestroy might have unsubscribed

      // Check if the timer should be visible based on the current route
      this.showTimer = true; // Set to true when the route is eligible for the timer
    } else {
      this.showTimer = false; // Set to false when the route is not eligible for the timer
    }
  }

  private unsubscribeFromStatus(): void {
    if (this.statusSubscription) {
      this.statusSubscription.unsubscribe();
      this.statusSubscription = null; // Reset the subscription variable
    }
    // Clear the existing interval when unsubscribing
    this.clearCountdownInterval();
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

  show(url: string): boolean {
    return (
      url.includes('/coding') ||
      url.includes('/start')
      // Add more conditions as needed for other components
    );
  }

  private clearCountdownInterval(): void {
    // Clear the existing interval if it's running
    // This prevents multiple intervals from running simultaneously
    clearInterval(this.countdownInterval);
    sessionStorage.removeItem(this.storageKey); // Clear stored state when stopping the interval
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

      // Reset the timer when the user logs in
      if (this.isLoggedIn) {
        this.resetTimer();
      }
    });
  }

  resetTimer(): void {
    this.countdownMinutes = 10; // Set the initial minutes value
    this.countdownSeconds = 0; // Set the initial seconds value
  }

  logout() {
    this.login.logout();
    this.isLoggedIn = this.login.isloggedin();
    this.user = this.login.getUser();
    this.router.navigate([`login`]);
  }

  startCountdown(): void {
    // Clear any existing interval before starting a new one
    this.clearCountdownInterval();

    this.countdownInterval = setInterval(() => {
      if (this.countdownSeconds > 0) {
        this.countdownSeconds--;
      } else if (this.countdownMinutes > 0) {
        this.countdownMinutes--;
        this.countdownSeconds = 59;
      } else {
        // When both minutes and seconds are zero, navigate and stop the interval
        clearInterval(this.countdownInterval);
        this.router.navigate(['/final']);
        this.status = false;
      }

      // Save the timer state on each tick
      this.saveTimerState();
    }, 1000);
  }

  logouthere(url: string): boolean {
    // Add logic to check if the timer should be visible for specific routes/components
    return (
      url.includes('/final') ||
      url.includes('/login')
      // Add more conditions as needed for other components
    );
  }

  ngOnDestroy(): void {
    // Unsubscribe to avoid memory leaks
    this.unsubscribeFromStatus();

    // Unsubscribe from route changes
    if (this.routeSubscription) {
      this.routeSubscription.unsubscribe();
    }

    // Clear the interval and stored state when the component is destroyed
    this.clearCountdownInterval();
  }

  submit() {
    this.router.navigate(['./final']);
  }

  private saveTimerState(): void {
    const timerState = {
      countdownMinutes: this.countdownMinutes,
      countdownSeconds: this.countdownSeconds,
      status: this.status,
    };
    sessionStorage.setItem(this.storageKey, JSON.stringify(timerState));
  }
}