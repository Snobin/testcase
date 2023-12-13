import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  constructor(public obj: AppComponent,private router:Router) { }

  ngOnInit(): void {
    this.obj.isLoggedIn=true;
  }
  backToLogin(){
    this.router.navigate([`./login`]);
  }
}
