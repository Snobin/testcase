import { Component, OnInit } from '@angular/core';
import { LoginComponent } from 'src/app/pages/login/login.component';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  isLoggedIn=false;
  user=null;

  constructor(public login:LoginService) { }

  ngOnInit(): void {
    this.isLoggedIn=this.login.isloggedin();
    this.user=this.login.getUser();
    this.login.loginStatusSubject.asObservable().subscribe((data) => {
      this.isLoggedIn=this.login.isloggedin();
    this.user=this.login.getUser();
    }
    );

  }
  
  logout(){
    this.login.logout();  
    window.location.reload();
  }

}
