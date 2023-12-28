import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ServiceService } from 'src/app/service/service.service';
import { CodeRequest } from '../../model/code-request';

declare var CodeMirror: any;
@Component({
  selector: 'app-coding-test',
  templateUrl: './coding-test.component.html',
  styleUrls: ['./coding-test.component.css']
})
export class CodingTestComponent implements OnInit {

  @ViewChild('editor', { static: false }) editorTextarea: ElementRef;

  private editor: any;
  selectedLanguage: string = 'java';
  testCases: string[] = [];
  result: any = { success: true, output: '' };
  code: string = '';
  codereq: CodeRequest = new CodeRequest();
  executionTime: any = 0;
  case: any=1;
  testInput11: any;
  testInput12: any;
  testInput21: any;
  testInput22: any;
  testInput31: any;
  testInput32: any;

  constructor(private apiService: ServiceService) { }

  ngOnInit(): void {
  }

  ngAfterViewInit() {
    if (this.selectedLanguage=='java') {
      this.editor = CodeMirror.fromTextArea(this.editorTextarea.nativeElement, {
        mode: "text/x-java",
        theme: "darcula",
        lineNumbers: true,
        autoCloseBrackets: true,
      });
    } else if (this.selectedLanguage=='cpp') {
      this.editor = CodeMirror.fromTextArea(this.editorTextarea.nativeElement, {
        mode: "text/x-c++src",
        theme: "darcula",
        lineNumbers: true,
        autoCloseBrackets: true,
      });
    } else if (this.selectedLanguage=='python') {
      this.editor = CodeMirror.fromTextArea(this.editorTextarea.nativeElement, {
        mode: "text/x-python",
        theme: "darcula",
        lineNumbers: true,
        autoCloseBrackets: true,
      });
    } else if (this.selectedLanguage=='c') {
      this.editor = CodeMirror.fromTextArea(this.editorTextarea.nativeElement, {
        mode: "text/x-csrc",
        theme: "darcula",
        lineNumbers: true,
        autoCloseBrackets: true,
      });
    } else {
      this.editor = CodeMirror.fromTextArea(this.editorTextarea.nativeElement, {
        mode: "text/x-java",
        theme: "darcula",
        lineNumbers: true,
        autoCloseBrackets: true,
      });
    }
    // const width = window.innerWidth * 0.7;
    // this.editor.setSize(width, 500);
    console.log(this.selectedLanguage);
    
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

  clear() {
    this.code = '';
  }

}
