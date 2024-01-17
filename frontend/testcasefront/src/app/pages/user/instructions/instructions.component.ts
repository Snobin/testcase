import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CodeService } from 'src/app/services/code.service';
import { QuestionService } from 'src/app/services/question.service';
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
  constructor(private route: ActivatedRoute, private quiz: QuizService, private router: Router, private question: QuestionService) { }

  ngOnInit(): void {
    this.qid = this.route.snapshot.params.qid;
    if (this.qid.startsWith('Q')) {
      this.question.getCodingQuestion(this.qid).subscribe(
        (data: any) => {
          this.quizdata = data;
        },
        (error) => {

        }
      )

    } else {
      this.quiz.getQuiz(this.qid).subscribe(
        (data: any) => {
          this.quizdata = data;
        },
        (error) => {

        }
      )
    }
  }
  next() {
    Swal.fire({
      title: 'Do you want to start the Assesment?',
      showCancelButton: true,
      confirmButtonText: 'Start',
      denyButtonText: "Don't Save",
      icon: 'info',
    }).then((result) => {
      if (result.isConfirmed) {
        this.router.navigate([`./user-dashboard`])
      }
    });
  }
}
