import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { QuestionService } from 'src/app/services/question.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-update-question',
  templateUrl: './update-question.component.html',
  styleUrls: ['./update-question.component.css']
})
export class UpdateQuestionComponent implements OnInit {

  qId;
  qTitle;
  questId;
  question = {
    content: '',
    option1: '',
    option2: '',
    option3: '',
    option4: '',
    answer: '',
      qId: '',
  };

  constructor(private route:ActivatedRoute, private service:QuestionService, private router: Router) { }

  ngOnInit(): void {
    this.qTitle = this.route.snapshot.params.title;
    this.qId = this.route.snapshot.params.qid;
    
    this.questId= this.route.snapshot.params.questId;
    console.log(this.questId);
    this.question.qId = this.qId;
    this.getdata();
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
    this.service.updateQuestion(this.question).subscribe(
      (data:any) => {
        this.question.content = '';
        this.question.option1 = '';
        this.question.option2 = '';
        this.question.option3 = '';
        this.question.option4 = '';
        this.question.answer = '';
        Swal.fire('Success', 'Question Added','success');
        this.router.navigate(['/admin/view-questions/'+this.qId+'/'+this.qTitle]);
      },(error) => {
        Swal.fire('Error', 'Error in adding question','error');
      }
    )
  }
  getdata(){
    this.service.getQuestion(this.questId).subscribe(
      (data:any) => {
        this.question=data;
      },(error) => {
        Swal.fire('Error', 'Error in adding question','error');
      }
    )
  }

}

