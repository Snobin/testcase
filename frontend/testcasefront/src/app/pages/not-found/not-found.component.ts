import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';

declare var anime: any;
@Component({
  selector: 'app-not-found',
  templateUrl: './not-found.component.html',
  styleUrls: ['./not-found.component.css']
})
export class NotFoundComponent implements OnInit {

  constructor(private router: Router,public login:LoginService) { }

  ngOnInit(): void {
    this.animateRow();
    this.animateZero();
  }

  animateRow(): void {
    anime({
      targets: '.row svg',
      translateY: 10,
      autoplay: true,
      loop: true,
      easing: 'easeInOutSine',
      direction: 'alternate',
    });
  }

  animateZero(): void {
    anime({
      targets: '#zero',
      translateX: 10,
      autoplay: true,
      loop: true,
      easing: 'easeInOutSine',
      direction: 'alternate',
      scale: [{ value: 1 }, { value: 1.4 }, { value: 1, delay: 250 }],
      rotateY: { value: '+=180', delay: 200 },
    });
  }
  backToLogin(){
    this.router.navigate(['./login']);
  }

}
