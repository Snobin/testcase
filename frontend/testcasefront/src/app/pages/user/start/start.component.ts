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
  isSubmit = false;
  timer: any;
  constructor(private locationst: LocationStrategy,
    private route: ActivatedRoute,
    private question: QuestionService) { }

  ngOnInit(): void {
    this.preventBackButton();
    this.qId = this.route.snapshot.params.qid;
    this.loadQuestions();
  }

  loadQuestions() {
    this.question.getQuestionsForQuiz(this.qId).subscribe(
      (data: any) => {
        this.questions = data;
        this.timer = this.questions.length * 2 * 60;
        this.questions.forEach((q) => {
          q['givenAnswer'] = '';
        });
        this.startTimer();
      },
      (error) => {
        Swal.fire("Error", "Error in loading questions");
      }
    );
  }

  preventBackButton() {
    history.pushState(null, null, location.href);
    this.locationst.onPopState(() => {
      history.pushState(null, null, location.href)
    });
  }
  submitQuiz() {
    Swal.fire({
      title: 'Do you want to submit the quiz?',
      showCancelButton: true,
      confirmButtonText: 'Submit',
      denyButtonText: 'Dont Save',
      icon: 'info'
    }).then((e) => {
      if (e.isConfirmed) {
        this.eval();
      }
    });
  }
  startTimer() {
    let t: any = window.setInterval(() => {
      if (this.timer <= 0) {
        this.eval();
        clearInterval(t);
      } else {
        this.timer--;
      }
    }, 1000)
  }
  getFormattedTime() {
    let mm = Math.floor(this.timer / 60);
    let ss = this.timer - mm * 60;
    // Formatting single-digit seconds
    let formattedSS = ss < 10 ? `0${ss}` : ss;
    return `${mm} min : ${formattedSS} sec`;
  }
  eval() {
    // this.isSubmit = true;
    // this.questions.forEach(q => {

    //   if (q.givenAnswer == q.answer) {
    //     this.correctAnswer++;
    //     let marksSingle =
    //       this.questions[0].quiz.maxMarks / this.questions.length;
    //     this.marksGot += marksSingle;
    //   }
    //   if (q.givenAnswer.trim() != '') {
    //     this.attempted++;
    //   }
    // });
    // console.log(this.correctAnswer);
    // console.log(this.marksGot);
    // console.log(this.attempted);

    this.question.evalQuiz(this.questions).subscribe(
      (data: any) => {
        this.attempted = data.attempted;
        this.correctAnswer = data.correctAnswers;
        this.marksGot = data.marksGot;
        this.isSubmit = true;
      },
      (error) => {

      }
    )
  }
}

