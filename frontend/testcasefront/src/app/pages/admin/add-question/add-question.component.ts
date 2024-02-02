import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { QuestionService } from 'src/app/services/question.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-question',
  templateUrl: './add-question.component.html',
  styleUrls: ['./add-question.component.css']
})
export class AddQuestionComponent implements OnInit {

  qId;
  qTitle;
  question = {
    content: null,
    option1: '',
    option2: '',
    option3: null,
    option4: null,
    answer: '',
    quiz: {
      qid: '',
    },
  };
  validationMessage={
    content:'',
    option1: '',
    option2: '',
    option3: '',
    option4: '',
    answer:''
  };

  constructor(private route:ActivatedRoute, private service:QuestionService, private router: Router) { }

  ngOnInit(): void {
    this.qId = this.route.snapshot.params.qid;
    this.qTitle = this.route.snapshot.params.title;
    this.question.quiz.qid = this.qId;
  }

  formSubmit(){
    if (this.question.content.trim() == '' || this.question.content ==null || this.question.content ==undefined) {
      return;
    }
    if (this.question.option1.trim() == '' || this.question.option1 ==null || this.question.option1 ==undefined) {
      return;
    }
    if (this.question.option2.trim() == '' || this.question.option2 ==null || this.question.option2 ==undefined) {
      return;
    }
    if (this.question.answer.trim() == '' || this.question.answer ==null || this.question.answer ==undefined) {
      return;
    }
    // form submit
    this.service.addQuestion(this.question).subscribe(
      (data:any) => {
        if(!data.details){
          this.question.content = '';
          this.question.option1 = '';
          this.question.option2 = '';
          this.question.option3 = null;
          this.question.option4 = null;
          this.question.answer = '';
          Swal.fire('Success', 'Question Added','success');
        }else{
          data.details.forEach(element => {
            var key = Object.keys(element)[0];
            this.validationMessage[key] = element[key];
        });
        }
      },(error) => {
        Swal.fire('Error', 'Error in adding question','error');
      }
    )
  }

}
