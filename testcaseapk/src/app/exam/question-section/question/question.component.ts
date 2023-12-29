import { Component , ElementRef, OnInit } from '@angular/core';
import { Student } from '../model/student';
import Swal from 'sweetalert2';
import { QuestionService } from '../services/question.service';
import { Question } from '../model/question';

declare var $:any;
declare var jQuery:any;
@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.css']
})
export class QuestionComponent implements OnInit {

  Obj:QuestionComponent;
  Questions:Question[]=[];

  pageSize: number = 1;
  currentPage: any = 1;
  displayedQuestions: any[] = [];
  pages: number[] = [];

  students:Student[] = [];

  totalPages:number;
  attemptedNo: any = 0;
  notAttemptedNo: any;


  constructor(private el:ElementRef, private questionService: QuestionService) { }

  ngOnInit(): void {
    this.getQuestions();
  }

  getQuestions(){
    this.questionService.getQuestions().subscribe( (data) => {
      this.Questions = data;
      this.setPage(1);
      for (let index = 0; index < this.totalPages; index++) {
        const student = new Student();
        student.studentId = '1';
        student.questionId = '';
        student.no = index+1;
        student.answer = '';
        student.status = '';
        this.students[index] = student;
      }
      this.notAttemptedNo = this.totalPages;
    });
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
  
  next(no:number, questionId:string){
    const existingStudentIndex = this.students.findIndex(student => student.no === no);
    if (existingStudentIndex !== -1) {
      this.students[existingStudentIndex].status = 'not attempted';
    }
    const value=$('input[name="options"]:checked').val();
    this.onRadioChange(value, no, questionId);
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

  save(no:number, questionId:string){
    const existingStudentIndex = this.students.findIndex(student => student.no === no);
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
    this.onRadioChange(value, no, questionId);
  }

  previous(no:number, questionId:string){
    const value=$('input[name="options"]:checked').val();
    this.onRadioChange(value, no, questionId);
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

  onRadioChange(value: string, no: number, questionId:string) {
    if (value != undefined || value != null) {
      const existingStudentIndex = this.students.findIndex(student => student.no === no);
      if (existingStudentIndex !== -1) {
        if (this.students[existingStudentIndex].answer=='') {
          this.students[existingStudentIndex].answer = value;
          this.students[existingStudentIndex].questionId = questionId;
          this.students[existingStudentIndex].status = 'attempted';
          this.attemptedNo++;
          this.notAttemptedNo--;
        } else if (this.students[existingStudentIndex].answer!='') {
          this.students[existingStudentIndex].answer = value;
          this.students[existingStudentIndex].questionId = questionId;
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
