import { Component, OnInit } from '@angular/core';
import { ServiceService } from '../service/service.service';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.css']
})
export class LandingComponent implements OnInit {
selectedLanguage: any;



  ngOnInit(): void {
  }
  
  testCases: string[] = [];
  result: any;
  code: string = '';

  constructor(private apiService: ServiceService, public obj:AppComponent) { }

  compileAndTest() {
    if(this.selectedLanguage=="java") {
      this.apiService.javaCompile(this.code).subscribe(
        (response) => {
          this.result = response;
        },
        (error) => {
          this.result = { success: false, errorMessage: 'Failed to communicate with the server.' };
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
