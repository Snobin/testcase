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
    option3: null,
    option4: null,
    answer: '',
      qId: '',
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
    this.qTitle = this.route.snapshot.params.title;
    this.qId = this.route.snapshot.params.qid;
    
    this.questId= this.route.snapshot.params.questId;
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
    this.service.updateQuestion(this.question).subscribe(
      (data:any) => {
        if(!data.details){
        this.question.content = '';
        this.question.option1 = '';
        this.question.option2 = '';
        this.question.option3 = null;
        this.question.option4 = null;
        this.question.answer = '';
        Swal.fire('Success', 'Question Updated','success');
        this.router.navigate(['/admin/view-questions/'+this.qId+'/'+this.qTitle]);
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

