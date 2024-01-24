import { LocationStrategy } from '@angular/common';
import { Component, HostListener, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { AddQuestion } from 'src/app/model/AddQuestion';
import { CategoryService } from 'src/app/services/category.service';
import { CodeService } from 'src/app/services/code.service';
import { FullScreenService } from 'src/app/services/full-screen.service';
import { QuizService } from 'src/app/services/quiz.service';
import Swal from 'sweetalert2';

@Component({
    selector: 'app-load-quiz',
    templateUrl: './load-quiz.component.html',
    styleUrls: ['./load-quiz.component.css']
})
export class LoadQuizComponent implements OnInit {

    

    catId;
    quizzes: any = [];
    codeInput: AddQuestion = new AddQuestion();
    codingQuestions: any = [];
    selectedQuestionType: string;
    title: any;
    categories: any;
    constructor(private cat: CategoryService, private route: ActivatedRoute, private locationst: LocationStrategy, private router: Router, private quiz: QuizService, private code: CodeService, private snack: MatSnackBar,private fullScreenService: FullScreenService) { }
    ngOnInit(): void {
        this.fullScreenService.requestFullScreen();
        this.showList();
    }

    showList() {
        this.route.params.subscribe((params) => {
            this.catId = params.catId;
            this.title = params.title;
            if (this.title === "CODING") {
                this.getCodingQuestions();
            } else {
                this.getActiveQuizCategory(this.catId);
            }
        });
    }

    preventBackButton() {
        history.pushState(null, null, location.href);
        this.locationst.onPopState(() => {
            history.pushState(null, null, location.href)
        });
    }

    getActiveQuizCategory(catid) {
        this.quiz.getActiveQuizCategory(catid).subscribe(
            (data: any) => {
                this.quizzes = data;
            },
            (error) => {
                console.log(error);
                alert('Error in loading specific quiz');
            }
        );
    }

    getCodingQuestions() {

        let userObject = localStorage.getItem("user");
        const user = JSON.parse(userObject);
        const username = user.username;

        this.code.activeCodingQuestions(username).subscribe(
            (data: any) => {
                this.codingQuestions = data;
                console.log(data);
                console.log(this.codingQuestions);
            },
            (error) => {
                console.log(error);
                Swal.fire('Error!', 'Error in loading coding questions!', 'error');
            }
        );
    }

    coding(qid: string) {
        this.router.navigate([`./coding/${qid}`])
    }

    question(qid: string) {
        this.router.navigate([`./start/${qid}`])
    }

    handleKeyDown(event: KeyboardEvent): void {
        this.fullScreenService.onKeyDown(event);
      }
      @HostListener('document:visibilitychange', ['$event'])
      onVisibilityChange(event: Event): void {
        if (event) {
            Swal.fire('Hello, this is a SweetAlert message!');
        } else {
          // Tab is visible again
        }
      }
      @HostListener('document:keydown', ['$event'])
      onKeyDown(event: KeyboardEvent): void {
        if (event.ctrlKey && event.key === 'Tab') {
          event.preventDefault(); // Prevent default tab-switching behavior
          Swal.fire('Hello, this is a SweetAlert message!');
        }
      }
    
}
