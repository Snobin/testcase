import { Component, OnInit } from '@angular/core';
import { Student } from '../student';

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.css']
})
export class QuestionComponent implements OnInit {

  Questions:any=[]=[
    {
      "id":"1",
      "no":"1",
      "desc":"qwerty",
      "answer":"ans",
      "a":"option1",
      "b":"option2",
      "c":"option3",
      "d":"option4",
      "e":"option5"
    },
    {
      "id":"2",
      "no":"2",
      "desc":"abcdef",
      "answer":"ans",
      "a":"optionA",
      "b":"optionB",
      "c":"optionC",
      "d":"optionD",
      "e":""
    },
    {
      "id":"3",
      "no":"3",
      "desc":"xyz",
      "answer":"ans",
      "a":"optionX",
      "b":"optionY",
      "c":"optionZ",
      "d":"",
      "e":""
    }
  ];
  students:Student[] = [];
  student = new Student();
  
  selectedOption: any;

  constructor() { }

  ngOnInit(): void {
  }
  
  onRadioChange(value: string, questionId: string) {
    this.selectedOption = value;
  
    if (this.selectedOption == undefined || this.selectedOption == null) {
      console.log("Please select an option to save");
    } else {
      const existingStudentIndex = this.students.findIndex(student => student.questionId === questionId);
      if (existingStudentIndex !== -1) {
        this.students[existingStudentIndex].answer = value;
      } else {
        const newStudent = new Student();
        newStudent.studentId="1";
        newStudent.questionId=questionId;
        newStudent.answer=value;
        this.students.push(newStudent);
      }
      console.log(this.students);
    }
  }

}
