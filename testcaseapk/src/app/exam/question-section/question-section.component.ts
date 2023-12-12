import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-question-section',
  templateUrl: './question-section.component.html',
  styleUrls: ['./question-section.component.css']
})
export class QuestionSectionComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }
  CodingTest(){
    this.router.navigate([`./exam/question-section/coding-test/2`]);
  }
  AptitudeTest(){
    this.router.navigate([`./exam/question-section/question/1`]);
  }

}
