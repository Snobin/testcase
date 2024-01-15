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
        console.log(data);
        console.log(this.quizzes);
      },
      (error) => {
        console.log(error);
        Swal.fire('Error !', 'Error in loading data !', 'error');
      }
    )
  }

  onToggleChange(questionId: string,act:any,q:any) {
    console.log("Question ID: ", questionId);
    this.codeInput = q;
    console.log(this.codeInput);
    this.codeInput.desc=q.description;
    this.codeInput.qid = questionId;
    this.codeInput.active =act;
    // Assuming you have a function in your code service to update the question's active status
    this.code.updateCode(this.codeInput,this.file).subscribe(
      (data: any) => {
        console.log(data);
      },
      (error) => {
        console.log(error);
      }
    );
  }
  
}
