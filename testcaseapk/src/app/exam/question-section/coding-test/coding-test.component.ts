import { Component, OnInit } from '@angular/core';
import { ServiceService } from 'src/app/service/service.service';
import { CodeRequest } from '../../model/code-request';

declare var $: any;
@Component({
  selector: 'app-coding-test',
  templateUrl: './coding-test.component.html',
  styleUrls: ['./coding-test.component.css']
})
export class CodingTestComponent implements OnInit {

  selectedLanguage: string;
  testCases: string[] = [];
  result: any;
  code: string = '';
  codereq:CodeRequest=new CodeRequest();

  constructor(private apiService: ServiceService) { }

  ngOnInit(): void {
    this.result.output="";
  }
  
  compileAndTest() {
    if(this.selectedLanguage=="java") {
      this.codereq.langId=this.selectedLanguage;
      this.codereq.code=this.code;

      this.apiService.javaCompile(this.codereq).subscribe(
        (response) => {
          this.result = response.output;
          console.log(this.result)
          JSON.stringify(this.result);
        },
        (error) => {
          this.result = error;
          console.log(error)
          if (error.status==400) {
            this.result.output = 'Bad Request';
          } else if (error.status==500) {
            this.result.output = 'Internal Server Error';
          }
        }
      );
    } else if(this.selectedLanguage=="python") {
      console.log(this.selectedLanguage)
      this.codereq.langId=this.selectedLanguage;
      this.codereq.code=this.code;
      this.apiService.pythonCompile(this.codereq).subscribe(
        (response) => {
          this.result = response.output;
          JSON.stringify(this.result);
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
