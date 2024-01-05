import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { QuizService } from 'src/app/services/quiz.service';

@Component({
  selector: 'app-load-quiz',
  templateUrl: './load-quiz.component.html',
  styleUrls: ['./load-quiz.component.css']
})
export class LoadQuizComponent implements OnInit {

  catId;
  quizzes;

  constructor(private route: ActivatedRoute,private quiz: QuizService) { }

  ngOnInit(): void {
    this.catId = this.route.snapshot.params.catId;
    if (this.catId  == 0) {
      this.quiz.quizzes().subscribe(
        (data:any) => {
          this.quizzes = data;
          console.log(this.quizzes);
          
        },
        (error) => {
          console.log(error);
          alert('Error in loading all quizzes')
        }
      );
    } else {
      console.log('load specific quiz');
      
    }
  }

}
