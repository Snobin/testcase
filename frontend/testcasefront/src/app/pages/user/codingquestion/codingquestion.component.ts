import { Component, OnInit } from '@angular/core';
import { CodeService } from 'src/app/services/code.service';

@Component({
  selector: 'app-codingquestion',
  templateUrl: './codingquestion.component.html',
  styleUrls: ['./codingquestion.component.css']
})
export class CodingquestionComponent implements OnInit {
  codingQuestions;

  constructor(private code: CodeService) { }

  ngOnInit(): void {
    this.code.activeCodingQuestions().subscribe(
      (data: any) => {
        this.codingQuestions = data;
        console.log(data);
        console.log(this.codingQuestions);
      },
      (error) => {
        console.log(error);
        // Handle error
      }
    );
  }
}