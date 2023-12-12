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
    "d":"option4",
    "e":"option5"},
    {"id":"2",
    "no":"2",
    "desc":"abcdef",
    "answer":"ans",
    "a":"optionA",
    "b":"optionB",
    "c":"optionC",
    "d":"optionD",
    "e":""},
    {"id":"3",
    "no":"3",
    "desc":"xyz",
    "answer":"ans",
    "a":"optionX",
    "b":"optionY",
    "c":"optionZ",
    "d":"",
    "e":""}
  ];

  constructor() { }

  ngOnInit(): void {
  }

}
