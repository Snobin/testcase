import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AddQuestion } from 'src/app/model/AddQuestion';
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
    constructor(private route: ActivatedRoute, private quiz: QuizService, private code: CodeService) { }
    ngOnInit(): void {
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
                this.quiz.getActiveQuizCategory(this.catId).subscribe(
                    (data: any) => {
                        this.quizzes = data;
                    },
                    (error) => {
                        console.log(error);
                        alert('Error in loading specific quiz');
                    }
                );
            }
        });
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


}
