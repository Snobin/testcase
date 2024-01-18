import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { CategoryService } from 'src/app/services/category.service';
import { CodeService } from 'src/app/services/code.service';
import { QuestionService } from 'src/app/services/question.service';
import { QuizService } from 'src/app/services/quiz.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-instructions',
  templateUrl: './instructions.component.html',
  styleUrls: ['./instructions.component.css']
})
export class InstructionsComponent implements OnInit {

  qid;
  quizdata;
  categories: any;
  constructor(private cat: CategoryService, private router: Router, private snack: MatSnackBar) { }

  ngOnInit(): void {

  }

  next() {
    Swal.fire({
      title: 'Do you want to start the Assesment?',
      showCancelButton: true,
      confirmButtonText: 'Start',
      denyButtonText: "Don't Save",
      icon: 'info',
    }).then((result) => {
      if (result.isConfirmed) {
        this.getCategories();
      }
    });
  }

  getCategories() {
    this.cat.categories().subscribe((data: any) => {
        this.categories = data;
        console.log(this.categories);
        this.router.navigate([`./user-dashboard/${this.categories[0].title}/${this.categories[0].cid}`]);
    },
        (error) => {
            this.snack.open('Error in loading categories from server', '', {
                duration: 3000,
            });
        }
    );
}
}
