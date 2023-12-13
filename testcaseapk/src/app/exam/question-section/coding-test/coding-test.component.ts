import { Component, OnInit } from '@angular/core';
import { ServiceService } from 'src/app/service/service.service';

declare var $: any;
@Component({
  selector: 'app-coding-test',
  templateUrl: './coding-test.component.html',
  styleUrls: ['./coding-test.component.css']
})
export class CodingTestComponent implements OnInit {

  selectedLanguage: any;
  testCases: string[] = [];
  result: any;
  code: string = '';

  constructor(private apiService: ServiceService) { }

  ngOnInit(): void {
    this.result.output="";
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
            this.result.output = 'Bad Request';
          } else if (error.status==500) {
            this.result.output = 'Internal Server Error';
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
            this.result.output = 'Bad Request';
          } else if (error.status==500) {
            this.result.output = 'Internal Server Error';
          }
        }
      );
    } else {
      this.result = { success: false, output: 'Please select a program language.' };
    }
  }
  clearCode(){
    this.code= "";
  }
  clear(){
    this.code= "";
    this.selectedLanguage="";
  }

}
