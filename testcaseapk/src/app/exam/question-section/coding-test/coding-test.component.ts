import { Component, OnInit } from '@angular/core';
import { ServiceService } from 'src/app/service/service.service';

import { CodeRequest } from '../../model/code-request';
import * as Prism from 'prismjs';


@Component({
  selector: 'app-coding-test',
  templateUrl: './coding-test.component.html',
  styleUrls: ['./coding-test.component.css']
})
export class CodingTestComponent implements OnInit {

  selectedLanguage: string = '';
  testCases: string[] = [];
  result: any = { success: true, output: '' };
  code: string = '';
  codereq: CodeRequest = new CodeRequest();
  executionTime: any = 0;

  constructor(private apiService: ServiceService) { }

  ngOnInit(): void {
    // Initialization logic, if any
  }

  async executeCode() {
    try {
      if (this.selectedLanguage && this.code) {
        this.codereq.langId = this.selectedLanguage;
        this.codereq.code = this.code;

        const response = await this.apiService.compileAndTest(this.codereq).toPromise();

        this.result.output = response.output;
        this.executionTime = response.processingTime;

      } else {
        this.result = { success: false, output: 'Please select a programming language and enter code.' };
      }
    } catch (error) {
      console.error('Error:', error);

      if (error.status === 400) {
        this.result.output = 'Bad Request';
      } else if (error.status === 500) {
        this.result.output = 'Internal Server Error';
      } else {
        this.result.output = 'An unexpected error occurred.';
      }
    }
  }

  clearCode() {
    this.code = '';
  }

  clear() {
    this.code = '';
    this.selectedLanguage = '';
  }

  highlightCode(){
    Prism.hightlightElement(document.getElementById('editor'));
  }

}
