import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ResultService } from 'src/app/services/result.service';

@Component({
  selector: 'app-ans-details',
  templateUrl: './ans-details.component.html',
  styleUrls: ['./ans-details.component.css']
})
export class AnsDetailsComponent implements OnInit {
  user: any;
  dataa: any; // Assuming dataa is of type any
  MCQ: boolean = true;
  Code: boolean = true;
  mcqQuestions: any[] = [];
  codingQuestions: any[] = [];

  constructor(private ans: ResultService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.user = params.userId;
      this.ans.getDataByUser(this.user).subscribe(
        (data: any) => {
          this.dataa = data;

          // Categorize questions based on their category
          this.dataa.mcqQuestions.forEach((mcqQuestion) => {
            this.mcqQuestions.push(mcqQuestion);
          });

          this.dataa.codingQuestions.forEach((codingQuestion) => {
            this.codingQuestions.push(codingQuestion);
          });
   
        },
        (error) => {
          console.error(error);
        }
      );
    });
  }
}
