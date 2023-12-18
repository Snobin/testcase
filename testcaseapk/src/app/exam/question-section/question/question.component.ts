import { Component , OnInit } from '@angular/core';
import { Student } from '../student';
import Swal from 'sweetalert2';

declare var $:any;
declare var jQuery:any;
@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.css']
})
export class QuestionComponent implements OnInit {

  Obj:QuestionComponent;
  Questions:any=[]=[
    {
      "id":"1",
      "no":"1",
      "desc":"qwerty",
      "answer":"a",
      "a":"option1",
      "b":"option2",
      "c":"option3",
      "d":"option4",
      "e":"option5"
    },
    {
      "id":"2",
      "no":"2",
      "desc":"b",
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
    },
    {
      "id":"4",
      "no":"4",
      "desc":"abcdef",
      "answer":"ans",
      "a":"optionA",
      "b":"optionB",
      "c":"optionC",
      "d":"optionD",
      "e":""
    },
    {
      "id":"5",
      "no":"5",
      "desc":"abcdef",
      "answer":"ans",
      "a":"optionA",
      "b":"optionB",
      "c":"optionC",
      "d":"optionD",
      "e":""
    },
    {
      "id":"6",
      "no":"6",
      "desc":"abcdef",
      "answer":"ans",
      "a":"optionA",
      "b":"optionB",
      "c":"optionC",
      "d":"optionD",
      "e":""
    },
    {
      "id":"7",
      "no":"7",
      "desc":"abcdef",
      "answer":"ans",
      "a":"optionA",
      "b":"optionB",
      "c":"optionC",
      "d":"optionD",
      "e":""
    },
    {
      "id":"8",
      "no":"8",
      "desc":"abcdef",
      "answer":"ans",
      "a":"optionA",
      "b":"optionB",
      "c":"optionC",
      "d":"optionD",
      "e":""
    },
    {
      "id":"9",
      "no":"9",
      "desc":"abcdef",
      "answer":"ans",
      "a":"optionA",
      "b":"optionB",
      "c":"optionC",
      "d":"optionD",
      "e":""
    },
    {
      "id":"10",
      "no":"10",
      "desc":"abcdef",
      "answer":"ans",
      "a":"optionA",
      "b":"optionB",
      "c":"optionC",
      "d":"optionD",
      "e":""
    },
    {
      "id":"11",
      "no":"11",
      "desc":"abcdef",
      "answer":"ans",
      "a":"optionA",
      "b":"optionB",
      "c":"optionC",
      "d":"optionD",
      "e":""
    },
    {
      "id":"12",
      "no":"12",
      "desc":"abcdef",
      "answer":"ans",
      "a":"optionA",
      "b":"optionB",
      "c":"optionC",
      "d":"optionD",
      "e":""
    },
    {
      "id":"13",
      "no":"13",
      "desc":"abcdef",
      "answer":"ans",
      "a":"optionA",
      "b":"optionB",
      "c":"optionC",
      "d":"optionD",
      "e":""
    },
    {
      "id":"14",
      "no":"14",
      "desc":"abcdef",
      "answer":"ans",
      "a":"optionA",
      "b":"optionB",
      "c":"optionC",
      "d":"optionD",
      "e":""
    }
  ];

  // Your array of questions
  pageSize: number = 1; // Number of questions per page
  currentPage: any = 1;
  displayedQuestions: any[] = [];
  pages: number[] = [];

  currentIndex: number = 0;
  students:Student[] = [];
  student = new Student();
  totalPages:number;
  existingStudentIndex:number;

  constructor() { }

  ngOnInit(): void {
    this.setPage(1);
  }

  setPage(page: number) {
    this.currentPage = page;
    const startIndex = (this.currentPage - 1) * this.pageSize;
    this.displayedQuestions = this.Questions.slice(startIndex, startIndex + this.pageSize);
    this.totalPages = Math.ceil(this.Questions.length / this.pageSize);
    this.pages = Array.from({ length: this.totalPages }, (_, i) => i + 1);
  }

  next(questionId:any) {
    const value=$('input[name="options"]:checked').val();
    this.onRadioChange(value, questionId);
    if (this.currentPage < this.pages.length) {
      this.setPage(this.currentPage + 1);
    } else {
      Swal.fire({
        text: 'You are on the last question already',
        showConfirmButton: false,
        timer: 600,
        position: 'top'
      })
    }
  }

  previous(questionId:any){
    const value=$('input[name="options"]:checked').val();
    this.onRadioChange(value, questionId);
    if (this.currentPage <= this.pages.length && this.currentPage != 1) {
      this.setPage(this.currentPage - 1);
    } else {
      Swal.fire({
        text: 'You are on the first question already',
        showConfirmButton: false,
        timer: 600,
        position: 'top'
      })
    }
  }

  onRadioChange(value: string, questionId: string) {
    if (value == undefined || value == null) {
      value='';
    } else {
      this.existingStudentIndex = this.students.findIndex(student => student.questionId === questionId);
      if (this.existingStudentIndex !== -1) {
        this.students[this.existingStudentIndex].answer = value;
        this.students[this.existingStudentIndex].attempted = true;
      } else {
        const newStudent = new Student();
        newStudent.studentId="1";
        newStudent.questionId=questionId;
        newStudent.answer=value;
        newStudent.attempted=true;
        this.students.push(newStudent);
      }
      console.log(this.students);
    }
  }

  

}
