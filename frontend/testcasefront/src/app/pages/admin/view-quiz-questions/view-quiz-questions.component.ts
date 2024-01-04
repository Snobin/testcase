import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { QuestionService } from 'src/app/services/question.service';

@Component({
  selector: 'app-view-quiz-questions',
  templateUrl: './view-quiz-questions.component.html',
  styleUrls: ['./view-quiz-questions.component.css']
})
export class ViewQuizQuestionsComponent implements OnInit {
  qId;
  qTitle;
  questions=[]
  constructor(
    private route:ActivatedRoute,
    private quest:QuestionService
    ) { }

  ngOnInit(): void {
    this.qId=this.route.snapshot.params.qid;
    this.qTitle=this.route.snapshot.params.title;
    console.log("hi",this.qId);

    this.quest.getQuestions(this.qId).subscribe((data:any)=>{
      console.log("hi",this.qId);
      this.questions=data;
    },
      (error)=>{
        console.log(error);
      }
      );

  }

}
