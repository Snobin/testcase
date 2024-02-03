import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { QuestionService } from 'src/app/services/question.service';
import Swal from 'sweetalert2';

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
    private quest:QuestionService,
    private router:Router
    ) { }

  ngOnInit(): void {
    this.qId=this.route.snapshot.params.qid;
    this.qTitle=this.route.snapshot.params.title;

    this.quest.getQuestions(this.qId).subscribe((data:any)=>{
      this.questions=data;
    },
      (error)=>{
        console.log(error);
      }
      );

  }

  // delete question
  deleteQuestion(questionId){
    Swal.fire({
      icon: 'info',
      showCancelButton: true,
      confirmButtonText: `Delete`,
      title: `Are you sure to delete the question?`
    }).then((result) =>{
      if (result.isConfirmed) {
        this.quest.deleteQuestion(questionId).subscribe(
          (data) => {
            Swal.fire({
              showConfirmButton: false,
              title: `Question deleted successfully`,
              position: 'top',
              background: '#28a745',
              color: '#fff',
              timer: 1000,
            });
            this.questions = this.questions.filter((q) => q.quesId != questionId);
          },
          (error) => {
            Swal.fire({
              showConfirmButton: false,
              title: `Error in deleting question`,
              position: 'top',
              background: '#dc3545',
              color: '#fff',
              timer: 1000,
            });
          }
        );
      }
    });
  }
 
}
