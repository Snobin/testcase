import { LocationStrategy } from '@angular/common';
import { Component, ElementRef, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';
import { QuestionService } from 'src/app/services/question.service';
import { UserService } from 'src/app/services/user.service';
import Swal from 'sweetalert2';

declare var $: any;
@Component({
  selector: 'app-start',
  templateUrl: './start.component.html',
  styleUrls: ['./start.component.css']
})
export class StartComponent implements OnInit {

  pageSize: number = 1;
  currentPage: any = 1;
  displayedQuestions: any[] = [];
  pages: number[] = [];
  students: any = [];
  totalPages: number;
  attemptedNo: any = 0;
  notAttemptedNo: any;

  qId;
  questions;
  marksGot = 0;
  correctAnswer = 0;
  isSubmit = false;
  timer: any;
  constructor(private el: ElementRef, private locationst: LocationStrategy,
    private route: ActivatedRoute,
    private question: QuestionService, private loginservice: LoginService) { }

  ngOnInit(): void {
    // this.preventBackButton();
    this.qId = this.route.snapshot.params.qid;
    this.loadQuestions();
  }

  loadQuestions() {
    this.question.getQuestionsForQuiz(this.qId).subscribe(
      (data: any) => {
        this.questions = data;
        this.setPage(1);
        this.timer = this.questions.length * 2 * 60;
        this.questions.forEach((q) => {
          q['givenAnswer'] = '';
          q['qId'] = this.qId;
          const userData = JSON.parse(localStorage.getItem('user'));
          q['user'] = userData.username;
        });
        // this.startTimer();
        for (let index = 0; index < this.totalPages; index++) {
          const student: any = {};
          student.studentId = '1';
          student.questionId = '';
          student.no = index + 1;
          student.answer = '';
          student.status = '';
          this.students[index] = student;
        }
        this.notAttemptedNo = this.totalPages;
      },
      (error) => {
        Swal.fire("Error", "Error in loading questions");
      }
    );
  }

  preventBackButton() {
    history.pushState(null, null, location.href);
    this.locationst.onPopState(() => {
      history.pushState(null, null, location.href)
    });
  }

  submitQuiz() {
    Swal.fire({
      title: 'Do you want to submit the quiz?',
      showCancelButton: true,
      confirmButtonText: 'Submit',
      denyButtonText: 'Dont Save',
      icon: 'info'
    }).then((e) => {
      if (e.isConfirmed) {
        this.eval();
      }
    });
  }

  // startTimer() {
  //   let t: any = window.setInterval(() => {
  //     if (this.timer <= 0) {
  //       this.eval();
  //       clearInterval(t);
  //     } else {
  //       this.timer--;
  //     }
  //   }, 1000)
  // }

  getFormattedTime() {
    let mm = Math.floor(this.timer / 60);
    let ss = this.timer - mm * 60;
    // Formatting single-digit seconds
    let formattedSS = ss < 10 ? `0${ss}` : ss;
    return `${mm} : ${formattedSS} Minutes`;
  }

  eval() {
    // this.isSubmit = true;
    // this.questions.forEach(q => {

    //   if (q.givenAnswer == q.answer) {
    //     this.correctAnswer++;
    //     let marksSingle =
    //       this.questions[0].quiz.maxMarks / this.questions.length;
    //     this.marksGot += marksSingle;
    //   }
    //   if (q.givenAnswer.trim() != '') {
    //     this.attempted++;
    //   }
    // });
    // console.log(this.correctAnswer);
    // console.log(this.marksGot);
    // console.log(this.attempted);

    this.question.evalQuiz(this.questions).subscribe(
      (data: any) => {
        this.attemptedNo = data.attempted;
        this.correctAnswer = data.correctAnswers;
        this.marksGot = data.marksGot;
        this.isSubmit = true;
      },
      (error) => {

      }
    )
  }

  setPage(page: number) {
    this.currentPage = page;
    const startIndex = (this.currentPage - 1) * this.pageSize;
    this.displayedQuestions = this.questions.slice(startIndex, startIndex + this.pageSize);
    this.totalPages = Math.ceil(this.questions.length / this.pageSize);
    this.pages = Array.from({ length: this.totalPages }, (_, i) => i + 1);
    for (let index = 0; index < page; index++) {
      const existingStudentIndex = this.students.findIndex(student => student.questionId === (index + 1).toString());
      if (existingStudentIndex !== -1) {
        if (this.students[existingStudentIndex].status != 'attempted') {
          this.students[existingStudentIndex].status = 'not attempted';
        }
      }
    }
  }

  next(no: number, questionId: string) {
    const existingStudentIndex = this.students.findIndex(student => student.no === no);
    if (existingStudentIndex !== -1) {
      this.students[existingStudentIndex].status = 'not attempted';
    }
    const value = $('input[name="options"]:checked').val();
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

  save(no: number, questionId: string) {
    const existingStudentIndex = this.students.findIndex(student => student.no === no);
    if (existingStudentIndex !== -1) {
      this.students[existingStudentIndex].status = 'not attempted';
    }
    const value = $('input[name="options"]:checked').val();
    if (value == undefined || value == null) {
      Swal.fire({
        text: 'Please select an option to save',
        showConfirmButton: false,
        timer: 600,
        position: 'top'
      })
    }
    this.onRadioChange(value, no, questionId);
  }

  previous(no: number, questionId: string) {
    const existingStudentIndex = this.students.findIndex(student => student.no === no);
    if (existingStudentIndex !== -1) {
      this.students[existingStudentIndex].status = 'not attempted';
    }
    const value = $('input[name="options"]:checked').val();
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

  onRadioChange(value: string, no: number, questionId: string) {
    if (value != undefined || value != null) {
      const existingStudentIndex = this.students.findIndex(student => student.no === no);
      if (existingStudentIndex !== -1) {
        if (this.students[existingStudentIndex].answer == '') {
          this.students[existingStudentIndex].answer = value;
          this.students[existingStudentIndex].questionId = questionId;
          this.students[existingStudentIndex].status = 'attempted';
          this.attemptedNo++;
          this.notAttemptedNo--;
        } else if (this.students[existingStudentIndex].answer != '') {
          this.students[existingStudentIndex].answer = value;
          this.students[existingStudentIndex].questionId = questionId;
          this.students[existingStudentIndex].status = 'attempted';
        }
      }
    }
    console.log(this.students);
  }

  status() {
    for (let index = 0; index < this.totalPages; index++) {
      if (this.students[index].status == 'attempted') {
        this.attemptedNo++;
        this.notAttemptedNo--;
      }
    }
  }

  scrollToTarget() {
    var no = this.currentPage;
    no = no + 1
    const id = '#button' + no;
    console.log(id)
    const targetElement = this.el.nativeElement.querySelector(id);
    if (targetElement) {
      targetElement.scrollIntoView({ behavior: 'smooth' });
    }
  }
}

