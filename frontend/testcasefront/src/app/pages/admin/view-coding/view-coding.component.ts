import { Component, OnInit } from '@angular/core';
import { CodeService } from 'src/app/services/code.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-view-coding',
  templateUrl: './view-coding.component.html',
  styleUrls: ['./view-coding.component.css']
})
export class ViewCodingComponent implements OnInit {
quizzes: any;

  constructor(private code: CodeService) { }

  ngOnInit(): void {
    this.code.Questions().subscribe(
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


  

}
