import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CategoryService } from 'src/app/services/category.service';

@Component({
  selector: 'app-sidebaruser',
  templateUrl: './sidebaruser.component.html',
  styleUrls: ['./sidebaruser.component.css']
})
export class SidebaruserComponent implements OnInit {
  categories;
  constructor(private cat: CategoryService, private snack: MatSnackBar) { }

  ngOnInit(): void {
    this.cat.categories().subscribe((data: any) => {
      this.categories = data;
      console.log(this.categories);
      
    },
      (error) => {
        this.snack.open('Error in loading categories from server', '', {
          duration: 3000,
        });
      }
    );
  }

}
