import { Component, OnInit } from '@angular/core';
import { QuizService } from 'src/app/services/quiz.service';

@Component({
  selector: 'app-mcq',
  templateUrl: './mcq.component.html',
  styleUrls: ['./mcq.component.css']
})
export class McqComponent implements OnInit {
  quizzes;

  constructor(private quiz: QuizService) { }

  ngOnInit(): void {
    // Fetch all quizzes
    this.quiz.getActiveQuizzes().subscribe(
      (data: any) => {
        this.quizzes = data;
        console.log(this.quizzes);
      },
      (error) => {
        console.log(error);
        // Handle error
      }
    );
  }
}