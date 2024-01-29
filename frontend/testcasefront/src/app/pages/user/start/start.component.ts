import { LocationStrategy } from '@angular/common';
import { Component, ElementRef, HostListener, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { CategoryService } from 'src/app/services/category.service';
import { FullScreenService } from 'src/app/services/full-screen.service';
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
  totalPages: number;
  attemptedNo: any = 0;
  notAttemptedNo: any;

  qId;
  questions;
  marksGot = 0;
  correctAnswer = 0;
  isSubmit = false;
  timer: any;
  categories: any;
  constructor(private el: ElementRef, private locationst: LocationStrategy,
    private route: ActivatedRoute, private router: Router, private cat: CategoryService, private snack: MatSnackBar,
    private question: QuestionService, private loginservice: LoginService, private fullScreenService: FullScreenService) { }

  ngOnInit(): void {
    // this.preventBackButton();
    this.fullScreenService.requestFullScreen();
    this.qId = this.route.snapshot.params.qid;
    this.loadQuestions();
  }

  loadQuestions() {
    this.question.getQuestionsForQuiz(this.qId).subscribe(
      (data: any) => {
        this.questions = data;
        console.log(this.questions);
        this.setPage(1);
        this.timer = this.questions.length * 2 * 60;
        this.questions.forEach((q) => {
          q['givenAnswer'] = null;
          q['status'] = '';
          q['qId'] = this.qId;
          const userData = JSON.parse(localStorage.getItem('user'));
          q['user'] = userData.username;
        });
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

  eval() {
    this.question.evalQuiz(this.questions).subscribe(
      (data: any) => {
        this.attemptedNo = data.attempted;
        this.correctAnswer = data.correctAnswers;
        this.marksGot = data.marksGot;
        this.isSubmit = true;
        this.getCategories();
      },
      (error) => {

      }
    )
  }

  getCategories() {
    this.cat.categories().subscribe((data: any) => {
      this.categories = data;
      console.log(this.categories);
      this.router.navigate([`./user-dashboard/${this.categories[0].title}/${this.categories[0].cid}`]);
    },
      (error) => {
        this.snack.open('Error in loading categories from server', '', {
          duration: 3000,
        });
      }
    );
  }

  setPage(page: number) {
    this.currentPage = page;
    const startIndex = (this.currentPage - 1) * this.pageSize;
    this.displayedQuestions = this.questions.slice(startIndex, startIndex + this.pageSize);
    this.totalPages = Math.ceil(this.questions.length / this.pageSize);
    this.pages = Array.from({ length: this.totalPages }, (_, i) => i + 1);
    for (let index = 0; index < page; index++) {
      if (index !== -1) {
        if (this.questions[index].status != 'attempted') {
          this.questions[index].status = 'not attempted';
        }
      }
    }
  }

  next(value: string, quesId: string) {
    const existingQuestionIndex = this.questions.findIndex(q => q.quesId === quesId);
    if (existingQuestionIndex !== -1) {
      this.questions[existingQuestionIndex].status = 'not attempted';
    }
    this.onRadioChange(value, quesId);
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

  save(value: string, quesId: string) {
    if (value == undefined || value == null) {
      Swal.fire({
        text: 'Please select an option to save',
        showConfirmButton: false,
        timer: 600,
        position: 'top'
      })
    } else {
      const existingQuestionIndex = this.questions.findIndex(q => q.quesId === quesId);
      if (existingQuestionIndex !== -1) {
        this.questions[existingQuestionIndex].status = 'not attempted';
      }
      this.onRadioChange(value, quesId);
    }
  }

  previous(value: string, quesId: string) {
    const existingQuestionIndex = this.questions.findIndex(q => q.quesId === quesId);
    if (existingQuestionIndex !== -1) {
      this.questions[existingQuestionIndex].status = 'not attempted';
    }
    this.onRadioChange(value, quesId);
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

  onRadioChange(value: string, quesId: string) {
    if (value != undefined || value != null) {
      const existingQuestionIndex = this.questions.findIndex(q => q.quesId === quesId);
      if (existingQuestionIndex !== -1) {
        if (this.questions[existingQuestionIndex].status == '' && this.questions[existingQuestionIndex].givenAnswer != '') {
          this.questions[existingQuestionIndex].status = 'attempted';
        } else if (this.questions[existingQuestionIndex].givenAnswer != '') {
          this.questions[existingQuestionIndex].status = 'attempted';
        }
      }
    }
    this.status();
  }

  status() {
    this.attemptedNo = 0;
    this.notAttemptedNo = this.questions.length;
    for (let index = 0; index < this.totalPages; index++) {
      if (this.questions[index].status == 'attempted') {
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

  @HostListener('document:keydown', ['$event'])
  private handleKeyboardEvent(event: KeyboardEvent): void {
    this.fullScreenService.onKeyDown(event);
  }

  @HostListener('document:visibilitychange', ['$event'])
  private handleVisibilityChange(event: Event): void {
    this.fullScreenService.onVisibilityChange(document.hidden);
  }

}

