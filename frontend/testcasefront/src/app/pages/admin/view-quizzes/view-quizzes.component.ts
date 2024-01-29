import { Component, OnInit } from '@angular/core';
import { QuizService } from 'src/app/services/quiz.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-view-quizzes',
  templateUrl: './view-quizzes.component.html',
  styleUrls: ['./view-quizzes.component.css']
})
export class ViewQuizzesComponent implements OnInit {

  quizzes = []
  constructor(private quiz: QuizService) { }

  ngOnInit(): void {
    this.quiz.quizzes().subscribe(
      (data: any) => {
        console.log(data);
        
        this.quizzes = data;
        console.log(this.quizzes);
      },
      (error) => {
        console.log(error);
        Swal.fire('Error !', 'Error in loading data !', 'error');
      }
    )
  }

  deleteQuiz(qId) {
   Swal.fire({
    icon:'info',
    title:'Are you sure ?',
    confirmButtonText:'Delete',
    showCancelButton:true,
   }).then((result)=>{
    if(result.isConfirmed){
      this.quiz.deleteQuiz(qId).subscribe(
        (data: any) => {
          this.quizzes = this.quizzes.filter((ans) => ans.qid !== qId); // Corrected property name
          Swal.fire('Success', 'Quiz deleted', 'success');
        },
        (error) => {
          Swal.fire('Error!', 'Error in deleting quiz. Please try again later.', 'error');
        }
      );
    }
   });
  }
  
}
