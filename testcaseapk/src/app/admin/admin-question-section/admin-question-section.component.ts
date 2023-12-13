import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-question-section',
  templateUrl: './admin-question-section.component.html',
  styleUrls: ['./admin-question-section.component.css']
})
export class AdminQuestionSectionComponent implements OnInit {
  test:boolean;
  constructor(private router: Router) { }

  ngOnInit(): void {
    this.test = false;
  }
  codingQuestions(){
    this.test=true;
    this.router.navigate([`./admin/admin-question-section/codingquestions`]);
  }
  mcq(){
    this.test=false;
    this.router.navigate([`./admin/admin-question-section/mcqquestions`]);
  }
}
