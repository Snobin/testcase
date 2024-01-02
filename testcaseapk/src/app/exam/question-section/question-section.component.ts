import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { QuestionComponent } from './question/question.component';

@Component({
  selector: 'app-question-section',
  templateUrl: './question-section.component.html',
  styleUrls: ['./question-section.component.css']
})
export class QuestionSectionComponent implements OnInit {

  test:boolean;
  class: any;
  countdownMinutes: number = 10;
  countdownSeconds: number = 0;

  constructor(private router: Router) { }

  ngOnInit(): void {
    this.startCountdown();
    this.AptitudeTest();
  }
  CodingTest(){
    this.test=true;
    this.class=false;
    this.router.navigate([`./exam/question-section/coding-test`]);
  }
  AptitudeTest(){
    this.test=false;
    this.class=true;
    this.router.navigate([`./exam/question-section/question`]);
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

}
