import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { QuizService } from 'src/app/services/quiz.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-instructions',
  templateUrl: './instructions.component.html',
  styleUrls: ['./instructions.component.css']
})
export class InstructionsComponent implements OnInit {

  qid;
  quizdata;
  constructor(private route:ActivatedRoute,private quiz:QuizService,private router:Router) { }

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
  startQuiz(){
    Swal.fire({
      title:'Do you want to start the quiz?',
      showCancelButton:true,
      confirmButtonText:'Start',
      denyButtonText:'Dont Save',
      icon:'info',
    }).then((result)=>{
      if(result.isConfirmed){
        this.router.navigate(['/start/'+this.qid]);
      } else if(result.isDenied){
        Swal.fire('changes are not saved','','info');
      }
    });
  }

}
