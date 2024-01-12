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

  quizzes: any;
  codeInput: AddQuestion = new AddQuestion();


  constructor(private code: CodeService) { }

  ngOnInit(): void {
    this.code.codingQuestions().subscribe(
      (data: any) => {
        this.quizzes = data;
        console.log(this.quizzes);
      },
      (error) => {
        console.log(error);
        Swal.fire('Error !', 'Error in loading data !', 'error');
      }
    )
  }

  onToggleChange() {
    console.log("hello");
    
    this.code.updateCode(this.codeInput).subscribe(
      (data: any) => {
        console.log(data);
      },
      (error) => {
        console.log(error)
      }
    )
  }
}
