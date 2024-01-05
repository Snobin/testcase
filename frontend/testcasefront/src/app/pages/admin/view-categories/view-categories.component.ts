import { Component, OnInit } from '@angular/core';
import { CategoryService } from 'src/app/services/category.service';
import { QuizService } from 'src/app/services/quiz.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-view-categories',
  templateUrl: './view-categories.component.html',
  styleUrls: ['./view-categories.component.css']
})
export class ViewCategoriesComponent implements OnInit {

  categories=[
    {
      cid:23,
      title:'programming',
      description:'this is testing category',
    },
    {
      cid:23,
      title:'programming',
      description:'this is testing category',
    },
    {
      cid:23,
      title:'programming',
      description:'this is testing category',
    },
    {
      cid:23,
      title:'programming',
      description:'this is testing category',
    },
  ]
  constructor(private category:CategoryService) { }

  ngOnInit(): void {
    this.category.categories().subscribe((data:any)=>{
      this.categories=data;
      console.log(this.categories);
    },
    (error)=>{
      console.log(error);
      Swal.fire('Error !!','Error in loading data','error');
    }
    )
  }



}
