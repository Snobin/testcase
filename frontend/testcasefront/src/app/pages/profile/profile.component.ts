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

  constructor(private login: LoginService,private router:Router) { }

  ngOnInit(): void {
    // this.user = this.login.getUser();
    this.login.getCurrentUser().subscribe(
      (user:any) => {
        this.user=user;
      },
      (error) => {
        alert("error")
      }
    )

  }
  update()
  {
    this.router.navigate(['/admin/update']);
  }


}
