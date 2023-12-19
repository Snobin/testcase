import { Component, OnInit } from '@angular/core';
import { McqQuestions } from '../../model/mcq-questions';
import { Router } from '@angular/router';
import { ServiceService } from 'src/app/service/service.service';



declare var $: any;
declare var jQuery: any;

@Component({
  selector: 'app-mcq-create',
  templateUrl: './mcq-create.component.html',
  styleUrls: ['./mcq-create.component.css']
})
export class McqCreateComponent implements OnInit {

  validationMessage:any;
  mcq: McqQuestions=new McqQuestions();
  constructor(private mcqService: ServiceService, private router:Router) { }
  ngOnInit(): void {
    this.validationMessage={};
  }
  addquestiondetail(){
    this.validationMessage={};
    let questionType =$('#questionId').val();;
    this.mcq.questionId=questionType;
    this.mcqService.addquestion(this.mcq).subscribe((data:any) =>{
      if (data.code.toLowerCase() == "success") {
        this.clear();
      } else {
        if (data.details) {
          data.details.forEach(element => {
            var key = Object.keys(element)[0];
            this.validationMessage[key] = element[key];
          });
        // console.log(this.validationMessage);
        }
      }
      console.log(data);
      // this.libraryList();
    },error=>console.log(error));
  }
  clear() {
    $('#bookTitle').val('');
    $('#bookId').val('');
    $('#memberName').val('');
    $('#memberContact').val('');
    $('#borrowingDate').val('');
    $('#idStatus').val("").trigger("change");
    $('#bookCategory').val("").trigger("change");
  }
  questionList(){
    this.router.navigate(['admin/admin-question-section/mcqquestions']);
  }
  onSubmit(){
  }

}
