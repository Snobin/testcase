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

  constructor(private router: Router) { }

  ngOnInit(): void {
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

}
