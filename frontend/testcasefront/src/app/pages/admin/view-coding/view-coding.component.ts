import { Component, OnInit } from '@angular/core';
import { AddQuestion } from 'src/app/model/AddQuestion';
import { CodeService } from 'src/app/services/code.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-view-coding',
  templateUrl: './view-coding.component.html',
  styleUrls: ['./view-coding.component.css']
})
export class ViewCodingComponent implements OnInit {
  file;

  quizzes: any;
  codeInput: AddQuestion = new AddQuestion();


  constructor(private code: CodeService) { }

  ngOnInit(): void {
    this.code.codingQuestions().subscribe(
      (data: any) => {
        this.quizzes = data;
        this.codeInput.qid=this.quizzes.questionId;
      
      },
      (error) => {
        Swal.fire('Error !', 'Error in loading data !', 'error');
      }
    )
  }

  onToggleChange(questionId: string,act:any,q:any) {
    this.codeInput = q;
    this.codeInput.desc=q.description;
    this.codeInput.qid = questionId;
    this.codeInput.ex1explanation=q.example1Exp;
    this.codeInput.ex1input=q.example1Input;
    this.codeInput.ex1output=q.example1Output;
    this.codeInput.ex2explanation=q.example2Exp;
    this.codeInput.ex2input=q.example2Input;
    this.codeInput.ex2output=q.example2Output;
    this.codeInput.active =act;
    this.code.updateCode(this.codeInput,this.file).subscribe(
      (data: any) => {
      },
      (error) => {
      }
    );
  }
  
}
