import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { AddQuestion } from 'src/app/model/AddQuestion';
import { CategoryService } from 'src/app/services/category.service';
import { CodeService } from 'src/app/services/code.service';
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
    constructor(private cat: CategoryService, private route: ActivatedRoute,private router: Router, private quiz: QuizService, private code: CodeService, private snack: MatSnackBar) { }
    ngOnInit(): void {
        this.showList();
    }

    showList(){
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
        // Your logic to fetch coding questions
        // Example:
        this.code.activeCodingQuestions().subscribe(
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

    coding(qid: string){
        this.router.navigate([`./coding/${qid}`])
    }

    question(qid: string){
        this.router.navigate([`./start/${qid}`])
    }
}
