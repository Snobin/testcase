import { Component, OnInit } from '@angular/core';
import { AppComponent } from 'src/app/app.component';
import { ServiceService } from 'src/app/service/service.service';

@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.css']
})
export class LandingComponent implements OnInit {

  selectedLanguage: any;
  testCases: string[] = [];
  result: any;
  code: string = '';

  constructor(private apiService: ServiceService, public obj:AppComponent) { }

  ngOnInit(): void {
    
  }
  
  compileAndTest() {
    if(this.selectedLanguage=="java") {
      this.apiService.javaCompile(this.code).subscribe(
        (response) => {
          this.result = response;
        },
        (error) => {
          this.result = error;
          if (error.status==400) {
            this.result.errorMessage = 'Bad Request';
          } else if (error.status==500) {
            this.result.errorMessage = 'Internal Server Error';
          }
        }
      );
    } else if(this.selectedLanguage=="python") {
      this.apiService.pythonCompile(this.code).subscribe(
        (response) => {
          this.result = response;
        },
        (error) => {
          this.result = error;
          if (error.status==400) {
            this.result.errorMessage = 'Bad Request';
          } else if (error.status==500) {
            this.result.errorMessage = 'Internal Server Error';
          }
        }
      );
    } else {
      this.result = { success: false, errorMessage: 'Please select a program language.' };
    }
  }

}
