import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { CodeRequest } from '../model/code-request';
import { CodeService } from 'src/app/services/code.service';
import { ActivatedRoute } from '@angular/router';
import { LocationStrategy } from '@angular/common';
import { trigger, state, style, transition, animate } from '@angular/animations';


declare var CodeMirror: any;
@Component({
  selector: 'app-console',
  templateUrl: './console.component.html',
  styleUrls: ['./console.component.css'],
  animations: [
    trigger('fadeInOut', [
      state('void', style({
        opacity: 0,
        transform: 'translateY(-20px)'
      })),
      transition(':enter, :leave', [
        animate('0.3s ease-in-out')
      ]),
    ]),
  ]
})
export class ConsoleComponent implements OnInit {
  qId: any;
  case1OutputMessage: any;
case2OutputMessage: any;




  @ViewChild('editor', { static: false }) editorTextarea: ElementRef;

  private editor: any;
  private output: any;
  selectedLanguage: string = 'java';
  testCases: string[] = [];
  result: any = { success: true, output: '// Your output will show here.', };
  code: string = '';
  codereq: CodeRequest = new CodeRequest();
  executionTime: any = 0;
  case: any = 1;
  testInput11: any;
  testInput12: any;
  testInput21: any;
  testInput22: any;
  testInput31: any;
  testInput32: any;

  questiondata;
  isOpen = false;
  case1: any;
  case2: any;
  case3: any;

  constructor(private service: CodeService, private route: ActivatedRoute, private locationst: LocationStrategy) { }

  // activateCase(caseNumber: number): void {
  //   // Reset all cases to false
  //   this.case1 = false;
  //   this.case2 = false;

  //   // Activate the selected case
  //   if (caseNumber === 0) {
  //     this.case1 = true;
  //   } else if (caseNumber === 1) {
  //     this.case2 = true;
  //   }

  //   // Additional logic or actions if needed
  // }
// Add a variable to track the active case
activeCase: number | null = null;

// Function to activate a case
activateCase(index: number): void {
  this.activeCase = index;
}


  ngOnInit(): void {
    this.clearAll();
    // this.preventBackButton();
    this.qId = this.route.snapshot.params.qid;
    this.qnData(this.qId);
  }

  ngAfterViewInit() {
    this.initializeCodeMirror();
  }
  preventBackButton() {
    history.pushState(null, null, location.href);
    this.locationst.onPopState(() => {
      history.pushState(null, null, location.href)
    });
  }

  toggleOpen() {
    this.isOpen = !this.isOpen;
  }
  toggle() {
    this.isOpen = true;
  }
  

  openOutput() {
    this.isOpen = true;
  }

  qnData(qid) {
    this.service.questionReq(qid).subscribe(
      (data: any) => {
        if (data) {
          this.questiondata = data;
          console.log(data);
        } else {
          console.error('No data received from the service.');
        }
      },
      (error) => {
        console.log(error);
      }
    );
  }


  get constraints(): string {
    // Replace all occurrences of '•' with '<br>•', except the first one
    const modifiedString = this.questiondata.constraints.replace(/(•)(?!$)/g, (_, first, index: number = 0) => {
      return index === 0 ? '•' : '<br>•';
    });
    return "<i>" + modifiedString + "</i>";
  }

  initializeCodeMirror() {
    if (this.editor) {
      // If the editor already exists, just set the new mode and theme
      this.editor.setOption("mode", this.getEditorMode());
      // Set the code value
      this.editor.setValue(this.code);
    } else {
      // If the editor doesn't exist, create it
      this.editor = CodeMirror.fromTextArea(this.editorTextarea.nativeElement, {
        mode: this.getEditorMode(),
        theme: "dracula",
        lineNumbers: true,
        autoCloseBrackets: true,
      });
      // Set the code to the CodeMirror editor
      this.editor.setValue(this.code);
    }
  }

  getEditorMode(): string {
    // Determine the CodeMirror mode based on the selected language
    if (this.selectedLanguage == 'java') {
      this.code = "// This program prints Hello, world! \n \n class HelloWorld { \n\tpublic static void main(String[] args) {\n\t\tSystem.out.println('Hello, World!'); \n\t}\n}";
      return "text/x-java";
    } else if (this.selectedLanguage == 'cpp') {
      this.code = "// This program prints Hello, world!\n\n#include <iostream>\n\nint main() {\n\tstd::cout << 'Hello World!';\n\treturn 0;\n}"
      return "text/x-c++src";
    } else if (this.selectedLanguage == 'python') {
      this.code = "# This program prints Hello, world!\n\nprint('Hello, world!')";
      return "text/x-python";
    } else if (this.selectedLanguage == 'c') {
      this.code = "// This program prints Hello, world! \n\n#include <stdio.h>\n\nint main() {\n\tprintf('Hello, World!');\n\treturn 0;\n}";
      return "text/x-csrc";
    } else {
      this.code = "// This program prints Hello, world! \n\n class HelloWorld { \n\tpublic static void main(String[] args) {\n\t\tSystem.out.println('Hello, World!'); \n\t}\n}";
      return "text/x-java";
    }
  }

  onChangeLang() {
    // Load the saved code from local storage if available
    const localStorageKey = `${this.selectedLanguage}EditorCode`;
    const savedCode = localStorage.getItem(localStorageKey);
    if (savedCode) {
      this.code = savedCode;
    }
    // Initialize CodeMirror
    this.initializeCodeMirror();
  }

  save() {
    // Save the current code to local storage before changing the language
    const localStorageKey = `${this.selectedLanguage}EditorCode`;
    localStorage.setItem(localStorageKey, this.editor.getValue());
    // Save the current code to local storage before changing the language
    const localStorageOutput = `${this.selectedLanguage}OutputCode`;
    localStorage.setItem(localStorageOutput, this.output.getValue());

  }

  async executeCode() {
    // Get the code from the CodeMirror editor
    this.toggle()
    this.code = this.editor.getValue();
    try {
      if (this.selectedLanguage && this.code) {
        this.codereq.langId = this.selectedLanguage;
        this.codereq.code = this.code;
        this.codereq.qnId = this.questiondata.questionId;
        const response = await this.service.compileAndTest(this.codereq).toPromise();
        this.result=response;
        this.result.output = response.output;
        this.executionTime = response.processingTime;
        console.log(this.result);
        
        this.initializeCodeMirror();
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
    // Remove the stored code in local storage
    const localStorageKey = `${this.selectedLanguage}EditorCode`;
    localStorage.removeItem(localStorageKey);
    this.code = '';
    // Clear the code in the CodeMirror editor
    this.editor.setValue('');
    // Remove the stored code in local storage
    const localStorageOutput = `${this.selectedLanguage}OutputCode`;
    localStorage.removeItem(localStorageOutput);
    this.result.output = '';
    // Clear the code in the CodeMirror Output
    this.editor.setValue('');
  }

  clearAll() {
    localStorage.removeItem('javaEditorCode');
    localStorage.removeItem('cppEditorCode');
    localStorage.removeItem('cEditorCode');
    localStorage.removeItem('pythonEditorCode');
    localStorage.removeItem('javaOutputCode');
    localStorage.removeItem('cppOutputCode');
    localStorage.removeItem('cOutputCode');
    localStorage.removeItem('pythonOutputCode');
  }


}
