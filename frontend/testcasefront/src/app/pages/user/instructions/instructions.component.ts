import { LocationStrategy } from '@angular/common';
import { Component, HostListener, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { NavbarComponent } from 'src/app/components/navbar/navbar.component';
import { CategoryService } from 'src/app/services/category.service';
import { CodeService } from 'src/app/services/code.service';
import { FullScreenService } from 'src/app/services/full-screen.service';
import { QuestionService } from 'src/app/services/question.service';
import { QuizService } from 'src/app/services/quiz.service';
import { UserService } from 'src/app/services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-instructions',
  templateUrl: './instructions.component.html',
  styleUrls: ['./instructions.component.css']
})
export class InstructionsComponent implements OnInit {

  qid;
  quizdata;
  obj: NavbarComponent;
  categories: any;
  constructor(private fullScreenService: FullScreenService, private cat: CategoryService, private router: Router, private snack: MatSnackBar, private userservice: UserService, private locationst: LocationStrategy) { }

  ngOnInit(): void {
    this.fullScreenService.requestFullScreen();
    this.updateStatus();
    this.preventBackButton();
    // this.preventDeveloper();
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

  updateStatus(): void {
    // Set the status to true or false as needed
    this.userservice.setStatus(true);
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

  preventBackButton() {
    history.pushState(null, null, location.href);
    this.locationst.onPopState(() => {
      history.pushState(null, null, location.href)
    });
  }

  @HostListener('document:visibilitychange', ['$event'])
  private handleVisibilityChange(event: Event): void {
    this.fullScreenService.onVisibilityChange(document.hidden);
  }

  @HostListener('document:keydown', ['$event'])
  private handleKeyboardEvent(event: KeyboardEvent): void {
    this.fullScreenService.onKeyDown(event);
  }
  
  preventDeveloper(){
    document.addEventListener("keydown", function (e) {
      if (e.key === "F12" || (e.ctrlKey && e.shiftKey && e.key === "I")) {
        e.preventDefault(); // Prevent opening developer tools
      }
    });
    
    document.addEventListener("contextmenu", function (e) {
      e.preventDefault(); // Prevent right-click context menu
    });
    
    // You can also check for other developer tools events and attempt to prevent them
    // window.addEventListener("resize", function () {
    //   if (window.outerWidth - window.innerWidth > 100 || window.outerHeight - window.innerHeight > 100) {
    //     Developer tools might be open
    //     alert("Please close the developer tools.");
    //   }
    // });
  }
}
