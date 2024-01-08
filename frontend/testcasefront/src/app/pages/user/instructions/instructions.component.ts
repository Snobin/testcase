import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { QuizService } from 'src/app/services/quiz.service';

@Component({
  selector: 'app-instructions',
  templateUrl: './instructions.component.html',
  styleUrls: ['./instructions.component.css']
})
export class InstructionsComponent implements OnInit {

  qid;
  quizdata;
  constructor(private route:ActivatedRoute,private quiz:QuizService) { }

  ngOnInit(): void {
    this.qid=this.route.snapshot.params.qid;
    this.quiz.getQuiz(this.qid).subscribe(
      (data:any)=>{
        this.quizdata=data;
      },
      (error)=>{

      }
    )
  }

}
