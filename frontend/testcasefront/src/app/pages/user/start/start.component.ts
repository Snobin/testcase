import { LocationStrategy } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { QuestionService } from 'src/app/services/question.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-start',
  templateUrl: './start.component.html',
  styleUrls: ['./start.component.css']
})
export class StartComponent implements OnInit {

  qId;
  questions;
  marksGot = 0;
  correctAnswer = 0;
  attempted = 0;
  isSubmit=false;
  constructor(private locationst: LocationStrategy,
    private route: ActivatedRoute,
    private question: QuestionService) { }

  ngOnInit(): void {
    this.preventBackButton();
    this.qId = this.route.snapshot.params.qid;
    this.loadQustions();
  }
  loadQustions() {
    this.question.getQuestionsForQuiz(this.qId).subscribe(
      (data: any) => {
        this.questions = data;
        this.questions.forEach((q) => {
            q['givenAnswer']='';
        });
      },
      (error) => {
        Swal.fire("Error", "Error in loading questions");
      }
    )
  }

  preventBackButton() {
    history.pushState(null, null, location.href);
    this.locationst.onPopState(() => {
      history.pushState(null, null, location.href)
    });
  }
  submitQuiz(){
    Swal.fire({
      title:'Do you want to submit the quiz?',
      showCancelButton:true,
      confirmButtonText:'Submit',
      denyButtonText:'Dont Save',
      icon:'info'
    }).then((e)=>{
      if(e.isConfirmed){
        this.isSubmit=true;
        this.questions.forEach(q=>{

          if(q.givenAnswer==q.answer)
          {
            this.correctAnswer++;
            let marksSingle =
                    this.questions[0].quiz.maxMarks/this.questions.length;
                    this.marksGot+=marksSingle;
          }
          if(q.givenAnswer.trim()!=''){
            this.attempted++;
          }
        });
        console.log(this.correctAnswer);
        console.log(this.marksGot);
        console.log(this.attempted);
      }
    });
    }
  }

