import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  isLoggedIn: any;
  user: any;

  constructor(  
      public login: LoginService,
      private router:Router
    ) { }

  ngOnInit(): void {
  }
  logout() {
    this.login.logout();
    this.isLoggedIn = this.login.isloggedin();
    this.user = this.login.getUser();
    this.router.navigate([`login`]);
  }

}
