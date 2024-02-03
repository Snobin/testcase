import { LocationStrategy } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  user = null;

  constructor(private login: LoginService, private router: Router, private locationst: LocationStrategy) { }

  ngOnInit(): void {
    this.preventBackButton();
    this.login.getCurrentUser().subscribe(
      (user: any) => {
        this.user = user;
      },
      (error) => {
        console.log(error);
      }
    )

  }
  update() {
    this.router.navigate(['/admin/update']);
  }

  preventBackButton() {
    history.pushState(null, null, location.href);
    this.locationst.onPopState(() => {
      history.pushState(null, null, location.href)
    });
  }
}
