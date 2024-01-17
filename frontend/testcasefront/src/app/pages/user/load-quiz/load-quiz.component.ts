import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
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
    quizzes;
    codeInput: AddQuestion = new AddQuestion();
    codingQuestions;
    selectedQuestionType: string;
    title: any;
    categories: any;
    constructor(private cat: CategoryService, private route: ActivatedRoute, private quiz: QuizService, private code: CodeService, private snack: MatSnackBar) { }
    ngOnInit(): void {
        this.getCategories();
        this.route.params.subscribe((params) => {
            this.catId = params.catId;
            this.title = params.title;
            console.log(this.catId);
            console.log(this.title);
            if (this.title === "CODING") {
                console.log("gggd");
                this.getCodingQuestions();
            }
            if (this.catId == 0) {
                // Fetch all quizzes
                // this.quiz.getActiveQuizzes().subscribe(
                //     (data: any) => {
                //         this.quizzes = data;
                //         console.log(this.quizzes);
                //     },
                //     (error) => {
                //         console.log(error);
                //         alert('Error in loading all quizzes');
                //     }
                // );
            } else {
                // Fetch specific quiz based on category ID
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

    getCategories() {
        this.cat.categories().subscribe((data: any) => {
            this.categories = data;
            console.log(this.categories);
            
            this.getActiveQuizCategory(this.categories[0].cid);
        },
            (error) => {
                this.snack.open('Error in loading categories from server', '', {
                    duration: 3000,
                });
            }
        );
    }
}
