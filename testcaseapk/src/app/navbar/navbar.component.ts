import { Component, OnInit } from '@angular/core';
import { LoginService } from '../service/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private loginService:LoginService, private router: Router) { }

  ngOnInit(): void {
  }

  isLogged(){
    return this.loginService.isloggedin();
  }

  logout(){
    this.loginService.logout();
    this.router.navigate([`./login`])
  }

}
