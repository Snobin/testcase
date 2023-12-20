import { Component , ElementRef, OnInit } from '@angular/core';
import { Student } from '../model/student';
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
      "e":"option5",
      "status":"not attempted"
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
      "e":"",
      "status":"not attempted"
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
      "e":"",
      "status":"not attempted"
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
      "e":"",
      "status":"not attempted"
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
      "e":"",
      "status":"not attempted"
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
      "e":"",
      "status":"not attempted"
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
      "e":"",
      "status":"not attempted"
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
      "e":"",
      "status":"not attempted"
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
      "e":"",
      "status":"not attempted"
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
      "e":"",
      "status":"not attempted"
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
      "e":"",
      "status":"not attempted"
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
      "e":"",
      "status":"not attempted"
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
      "e":"",
      "status":"not attempted"
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
      "e":"",
      "status":"not attempted"
    }
  ];

  pageSize: number = 1;
  currentPage: any = 1;
  displayedQuestions: any[] = [];
  pages: number[] = [];

  students:Student[] = [];
  totalPages:number;
  attemptedNo: any = 0;
  notAttemptedNo: any;

  constructor(private el:ElementRef) { }

  ngOnInit(): void {
    this.setPage(1);
    for (let index = 0; index < this.totalPages; index++) {
      const student = new Student();
      student.studentId = '1';
      student.questionId = (index+1).toString();
      student.answer = '';
      student.status = '';
      this.students[index] = student;
    }
    this.notAttemptedNo = this.totalPages;
  }

  setPage(page: number) {
    this.currentPage = page;
    const startIndex = (this.currentPage - 1) * this.pageSize;
    this.displayedQuestions = this.Questions.slice(startIndex, startIndex + this.pageSize);
    this.totalPages = Math.ceil(this.Questions.length / this.pageSize);
    this.pages = Array.from({ length: this.totalPages }, (_, i) => i + 1);
    for (let index = 0; index < page; index++) {
      const existingStudentIndex = this.students.findIndex(student => student.questionId === (index+1).toString());
      if (existingStudentIndex !== -1) {
        if(this.students[existingStudentIndex].status!='attempted'){
          this.students[existingStudentIndex].status = 'not attempted';
        }
      }
    }
  }
  
  next(questionId:any){
    const existingStudentIndex = this.students.findIndex(student => student.questionId === questionId);
    if (existingStudentIndex !== -1) {
      this.students[existingStudentIndex].status = 'not attempted';
    }
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

  save(questionId:any){
    const existingStudentIndex = this.students.findIndex(student => student.questionId === questionId);
    if (existingStudentIndex !== -1) {
      this.students[existingStudentIndex].status = 'not attempted';
    }
    const value=$('input[name="options"]:checked').val();
    if (value==undefined||value==null) {
      Swal.fire({
        text: 'Please select an option to save',
        showConfirmButton: false,
        timer: 600,
        position: 'top'
      })
    } else {
      // Swal.fire({
      //   text: 'Option '+value+' saved successfully',
      //   showConfirmButton: false,
      //   timer: 600,
      //   position: 'top'
      // })
    }
    this.onRadioChange(value, questionId);
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
    if (value != undefined || value != null) {
      const existingStudentIndex = this.students.findIndex(student => student.questionId === questionId);
      if (existingStudentIndex !== -1) {
        if (this.students[existingStudentIndex].answer=='') {
          this.students[existingStudentIndex].answer = value;
          this.students[existingStudentIndex].status = 'attempted';
          this.attemptedNo++;
          this.notAttemptedNo--;
        } else if (this.students[existingStudentIndex].answer!='') {
          this.students[existingStudentIndex].answer = value;
          this.students[existingStudentIndex].status = 'attempted';
        }
      }
    }
  }

  status(){
    for (let index = 0; index < this.totalPages; index++) {
      if (this.students[index].status=='attempted') {
        this.attemptedNo++;
        this.notAttemptedNo--;
      }
    }
  }

  scrollToTarget() {
    var no = this.currentPage;
    no=no+1
    const id='#button'+no;
    console.log(id)
    const targetElement = this.el.nativeElement.querySelector(id);
    if (targetElement) {
      targetElement.scrollIntoView({ behavior: 'smooth' });
    }
    
  }

}
