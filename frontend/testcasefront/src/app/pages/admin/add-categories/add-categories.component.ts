import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CategoryService } from 'src/app/services/category.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-categories',
  templateUrl: './add-categories.component.html',
  styleUrls: ['./add-categories.component.css']
})
export class AddCategoriesComponent implements OnInit {

  category = {
    title: '',
    description: ''
  }
  validationMessage = {
    title: '',
    description: ''
};

  constructor(private _category: CategoryService,
    private _snack: MatSnackBar
  ) { }

  ngOnInit(): void {
  }

  formSubmit() {
    if (this.category.title.trim() == '' || this.category.title == null) {
      this._snack.open("Title Required !!", '', {
        duration: 3000
      })
    }
    this._category.addCategory(this.category).subscribe((data: any) => {
      if(!data.details){
        this.category.description = '';
        this.category.title = '';
        Swal.fire('Success !!', 'Category is added successfully', 'success');
      }else{
        if(data.details){
          data.details.forEach(element => {
            var key = Object.keys(element)[0];
            this.validationMessage[key] = element[key];
          });
        }
      }
    },
      (error) => {
        console.log(error);
        Swal.fire('Error !!', 'Server error !!', 'error')
      }
    )
  }
}
