import { LocationStrategy } from '@angular/common';
import { Component, HostListener, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { AddQuestion } from 'src/app/model/AddQuestion';
import { CategoryService } from 'src/app/services/category.service';
import { CodeService } from 'src/app/services/code.service';
import { FullScreenService } from 'src/app/services/full-screen.service';
import { QuizService } from 'src/app/services/quiz.service';
import { UserService } from 'src/app/services/user.service';
import Swal from 'sweetalert2';

@Component({
    selector: 'app-load-quiz',
    templateUrl: './load-quiz.component.html',
    styleUrls: ['./load-quiz.component.css']
})
export class LoadQuizComponent implements OnInit {


    status: any = "Start";
    catId;
    quizzes: any = [];
    codeInput: AddQuestion = new AddQuestion();
    codingQuestions: any = [];
    selectedQuestionType: string;
    title: any;
    categories: any;
    time: number = 0;
    variable = 0;

    constructor(
        private cat: CategoryService,
        private route: ActivatedRoute,
        private locationst: LocationStrategy,
        private router: Router,
        private quiz: QuizService,
        private code: CodeService,
        private snack: MatSnackBar,
        private userservice: UserService,
        private fullScreenService: FullScreenService
    ) { }

    ngOnInit(): void {
        this.fullScreenService.requestFullScreen();
        this.showList();

        if (!localStorage.getItem("minutes")) {
            setTimeout(() => {
                console.log(this.time);
                this.updateStatus(this.time);
            }, 500);
        } else if (localStorage.getItem("minutes")) {
            setTimeout(() => {
                console.log(this.time);
                this.updateStatus(this.time);
            }, 0);
        }
    }

    updateStatus(time: any): void {
        console.log(time);
        if (!localStorage.getItem("minutes")) {
            localStorage.setItem("minutes", time);
        }
        this.userservice.setStatus(true, time);
    }

    preventBackButton() {
        history.pushState(null, null, location.href);
        this.locationst.onPopState(() => {
            history.pushState(null, null, location.href);
        });
    }

    showList() {
        this.route.params.subscribe((params) => {
            this.catId = params.catId;
            this.title = params.title;
            if (this.variable == 0) {
                this.variable++;

                this.quiz.getActiveQuizCategory(5).subscribe(
                    (data: any) => {
                        this.quizzes = data;
                        data.forEach((quiz: any) => {
                            const quizTime = quiz.time ? parseInt(quiz.time) : 0;
                            this.time += quizTime;
                        });
                        console.log(this.time);
                    },
                    (error) => {
                        console.log(error);
                    }
                );

                let userObject = localStorage.getItem("user");
                const user = JSON.parse(userObject);
                const username = user.username;

                this.code.activeCodingQuestions(username).subscribe(
                    (data: any) => {
                        this.codingQuestions = data;
                        data.forEach((quiz: any) => {
                            const quizTime = quiz.time ? parseInt(quiz.time) : 0;
                            this.time += quizTime;
                        });
                        console.log(this.time);
                        console.log(this.codingQuestions);
                    },
                    (error) => {
                        console.log(error);
                    }
                );
            }

            if (this.title === "CODING") {
                this.getCodingQuestions();
            } else {
                this.getActiveQuizCategory(this.catId);
            }
        });
    }

    getActiveQuizCategory(catid) {
        this.quiz.getActiveQuizCategory(catid).subscribe(
            (data: any) => {
                this.quizzes = data;
                for (let index = 0; index < data.length; index++) {
                    if (data[index].status != 'Review') {
                        data[index].status = 'Start';
                    }
                }
                if (!localStorage.getItem("quizzes")) {
                    localStorage.setItem("quizzes", JSON.stringify(data));
                }
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
                for (let index = 0; index < data.length; index++) {
                    if (data[index].status != 'Review') {
                        data[index].status = 'Start';
                    }
                }
                if (!localStorage.getItem("codingQuestions")) {
                    localStorage.setItem("codingQuestions", JSON.stringify(data));
                }
            },
            (error) => {
                console.log(error);
                Swal.fire('Error!', 'Error in loading coding questions!', 'error');
            }
        );
    }

    coding(qid: string) {
        this.router.navigate([`./coding/${qid}`]);
    }

    question(qid: string) {
        this.router.navigate([`./start/${qid}`]);
    }

    buttonStatusCoding(qid: string) {
        if (localStorage.getItem("codingQuestions")) {
            let codingQuestions = JSON.parse(localStorage.getItem("codingQuestions"));
            const index = codingQuestions.findIndex(c => c.questionId == qid);
            if (index != -1) {
                return codingQuestions[index].status;
            }
        }
    }

    buttonStatusQuestion(qid: string) {
        if (localStorage.getItem("quizzes")) {
            let quizzes = JSON.parse(localStorage.getItem("quizzes"));
            const index = quizzes.findIndex(q => q.qid == qid);
            if (index != -1) {
                return quizzes[index].status;
            }
        }
    }

    buttonColor(qid: string,type: string){
        let color = '';
        let status = '';
        if (type == "CODING") {
            status = this.buttonStatusCoding(qid);
            if (status == 'Review') {
                color = "success";
            } else {
                color = "primary"
            }
        } else if (type == "QUIZ") {
            status = this.buttonStatusQuestion(qid);
            if (status == 'Review') {
                color = "success";
            } else {
                color = "primary"
            }
        }
        return color;
    }

    @HostListener('document:visibilitychange', ['$event'])
    private handleVisibilityChange(event: Event): void {
        this.fullScreenService.onVisibilityChange(document.hidden);
    }

    @HostListener('document:keydown', ['$event'])
    private handleKeyboardEvent(event: KeyboardEvent): void {
        this.fullScreenService.onKeyDown(event);
    }
}
