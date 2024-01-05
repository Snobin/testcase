import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CategoryService } from 'src/app/services/category.service';
import { QuizService } from 'src/app/services/quiz.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-update-quiz',
  templateUrl: './update-quiz.component.html',
  styleUrls: ['./update-quiz.component.css']
})
export class UpdateQuizComponent implements OnInit {

  constructor(
    private cat:CategoryService,
     private route:ActivatedRoute,
     private router:Router,
     private quiz:QuizService) { }

  qId=0;
  quizdata;
  categories: any;


  ngOnInit(): void {
    this.qId=this.route.snapshot.params.qid;
    this.quiz.getQuiz(this.qId).subscribe(
      (data)=>{
        this.quizdata=data;  
      },
      (error)=>{
        console.log(error);
      }
    );
    this.cat.categories().subscribe((data:any)=>{
      this.categories=data;
    },
    (error)=>{

    }
    );
  }
  update(){
    this.quiz.updateQuiz(this.quizdata).subscribe(
      (data)=>{
        Swal.fire('Success','quiz updated','success').then((e)=>{
          this.router.navigate(['/admin/quizzes']);
        });
      },
      (error)=>{
        Swal.fire('Error','error in updating quiz','error');

      }
    )
  }

}
