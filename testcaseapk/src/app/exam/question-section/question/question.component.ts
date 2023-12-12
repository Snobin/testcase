import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.css']
})
export class QuestionComponent implements OnInit {

  Questions:any=[]=[
    {"id":"1",
    "no":"1",
    "desc":"qwerty",
    "answer":"ans",
    "a":"option1",
    "b":"option2",
    "c":"option3",
    "d":"option4"}
  ];

  constructor() { }

  ngOnInit(): void {
  }

}
