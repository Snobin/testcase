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
      this.categories.forEach((c) => {
        c['focus'] = false;
      });
      this.categories[0].focus = true;
    },
      (error) => {
        this.snack.open('Error in loading categories from server', '', {
          duration: 3000,
        });
      }
    );
  }

  setFocus(selectedCategory: any) {
    // Set focus to the selected category
    this.categories.forEach(category => (category.focus = false)); // Reset focus for all categories
    selectedCategory.focus = true;
  }

}
