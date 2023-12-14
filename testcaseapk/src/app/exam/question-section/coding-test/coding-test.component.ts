import { Component, OnInit } from '@angular/core';
import { ServiceService } from 'src/app/service/service.service';
import * as Prism from 'prismjs';
import * as rangy from 'rangy';


declare var $: any;
@Component({
  selector: 'app-coding-test',
  templateUrl: './coding-test.component.html',
  styleUrls: ['./coding-test.component.css']
})
export class CodingTestComponent implements OnInit {

  selectedLanguage: any='';
  testCases: string[] = [];
  result: any = { success:true, output: "" };
  code: string = '';

  constructor(private apiService: ServiceService) { }

  ngOnInit(): void {
  }
  
  compileAndTest() {
    if(this.selectedLanguage=="java") {
      this.apiService.javaCompile(this.code).subscribe(
        (response) => {
          this.result = { success: true, output: response };
        },
        (error) => {
          this.result = error;
          if (error.status==400) {
            this.result = { success: false, output: 'Bad Request' };
          } else if (error.status==500) {
            this.result = { success: false, output: 'Internal Server Error' };
          }
        }
      );
    } else if(this.selectedLanguage=="python") {
      this.apiService.pythonCompile(this.code).subscribe(
        (response) => {
          this.result = { success: true, output: response };
        },
        (error) => {
          this.result = error;
          if (error.status==400) {
            this.result = { success: false, output: 'Bad Request' };
          } else if (error.status==500) {
            this.result = { success: false, output: 'Internal Server Error' };
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

  highlightCode(): void {
    const editor = document.getElementById('editor');
    Prism.highlightElement(editor);
  }
}
