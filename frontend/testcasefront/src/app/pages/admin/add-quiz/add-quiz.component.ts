import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CategoryService } from 'src/app/services/category.service';
import { QuizService } from 'src/app/services/quiz.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-quiz',
  templateUrl: './add-quiz.component.html',
  styleUrls: ['./add-quiz.component.css']
})
export class AddQuizComponent implements OnInit {

  categories = []

  Data = {
    title: '',
    description: '',
    maxMarks: '',
    numberOfQuestions: '',
    active: true,
    time:'',
    category: {
      cid: ''
    },
  }

  constructor(private cat: CategoryService, private quiz: QuizService, private snack: MatSnackBar) { }

  ngOnInit(): void {
    this.cat.categories().subscribe(
      (data: any) => {
        this.categories = data;
        console.log(this.categories);
      },
      (error) => {
        console.log(error);
        Swal.fire('Error !!', 'error in loading data from server', 'error');
      }
    )
  }
  addQuiz() {
    if (this.Data.title.trim() == '' || this.Data.title == null) {
      this.snack.open('Title Required !!', '', {
        duration: 3000,
      });
      return;
    }

    this.quiz.addQuiz(this.Data).subscribe(
      (data: any) => {
        Swal.fire('Success', 'quiz is added', 'success');
        this.Data = {
          title: '',
          description: '',
          maxMarks: '',
          numberOfQuestions: '',
          active: true,
          time:'',
          category: {
            cid: ''
          },
        }
      },
      (error) => {
        Swal.fire('Error !!', 'error while adding quiz', 'error');

      }
    )
  } 
}
