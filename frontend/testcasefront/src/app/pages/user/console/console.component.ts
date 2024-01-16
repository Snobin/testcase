import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { CodeRequest } from '../model/code-request';
import { CodeService } from 'src/app/services/code.service';
import { ActivatedRoute } from '@angular/router';
import { LocationStrategy } from '@angular/common';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { Case } from '../model/case';


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
  selectedLanguage: string = 'java';
  testCases: string[] = [];
  result: any = { success: true, output: '// Your output will show here.', };
  code: string = '';
  codereq: CodeRequest = new CodeRequest();
  executionTime: any = 0;

  case1: boolean = false;
  case2: boolean = false;
  case3: boolean = false;
  case_1: Case = new Case();
  case_2: Case = new Case();
  case_3: Case = new Case();
  cases: Case[] = [
    {
      output: '0',
      input: '0',
      processingTime: 0,
      expectedOutput: '0',
      message: '-----------',
      success: ' ',
    },
    {
      output: '0',
      input: '0',
      processingTime: 0,
      expectedOutput: '0',
      message: '-----------',
      success: ' ',
    },
    {
      output: '0',
      input: '0',
      processingTime: 0,
      expectedOutput: '0',
      message: '-----------',
      success: ' ',
    },
  ];

  questiondata;
  isOpen = false;

  constructor(private service: CodeService, private route: ActivatedRoute, private locationst: LocationStrategy) { }

  ngOnInit(): void {
    this.clearAll();
    this.activateCase1();
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

  activateCase1() {
    if (this.case1 == false) {
      this.case1 = true;
      this.case2 = false;
      this.case3 = false;
    }
  }

  activateCase2() {
    if (this.case2 == false) {
      this.case1 = false;
      this.case2 = true;
      this.case3 = false;
    }
  }

  activateCase3() {
    if (this.case3 == false) {
      this.case1 = false;
      this.case2 = false;
      this.case3 = true;
    }
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
    this.case_1 = this.cases[0];
    this.case_2 = this.cases[1];
    this.case_3 = this.cases[2];
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
      if (!localStorage.getItem(`${this.selectedLanguage}EditorCode`)) {
        this.code = "// This program prints Hello, world! \n \n class HelloWorld { \n\tpublic static void main(String[] args) {\n\t\tSystem.out.println('Hello, World!'); \n\t}\n}";
      }
      return "text/x-java";
    } else if (this.selectedLanguage == 'cpp') {
      if (!localStorage.getItem(`${this.selectedLanguage}EditorCode`)) {
        this.code = "// This program prints Hello, world!\n\n#include <iostream>\n\nint main() {\n\tstd::cout << 'Hello World!';\n\treturn 0;\n}"
      } else {
        this.code = localStorage.getItem(`${this.selectedLanguage}EditorCode`)
      }
      return "text/x-c++src";
    } else if (this.selectedLanguage == 'python') {
      if (!localStorage.getItem(`${this.selectedLanguage}EditorCode`)) {
        this.code = "# This program prints Hello, world!\n\nprint('Hello, world!')";
      } else {
        this.code = localStorage.getItem(`${this.selectedLanguage}EditorCode`)
      }
      return "text/x-python";
    } else if (this.selectedLanguage == 'c') {
      if (!localStorage.getItem(`${this.selectedLanguage}EditorCode`)) {
        this.code = "// This program prints Hello, world! \n\n#include <stdio.h>\n\nint main() {\n\tprintf('Hello, World!');\n\treturn 0;\n}";
      } else {
        this.code = localStorage.getItem(`${this.selectedLanguage}EditorCode`)
      }
      return "text/x-csrc";
    } else {
      if (!localStorage.getItem(`${this.selectedLanguage}EditorCode`)) {
        this.code = "// This program prints Hello, world! \n \n class HelloWorld { \n\tpublic static void main(String[] args) {\n\t\tSystem.out.println('Hello, World!'); \n\t}\n}";
      } else {
        this.code = localStorage.getItem(`${this.selectedLanguage}EditorCode`)
      }
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

  }

  async executeCode() {
    // Get the code from the CodeMirror editor
    this.save();
    this.activateCase1();
    this.toggle();
    this.code = this.editor.getValue();
    try {
      if (this.selectedLanguage && this.code) {
        this.codereq.langId = this.selectedLanguage;
        this.codereq.code = this.code;
        this.codereq.qnId = this.questiondata.questionId;
        const response = await this.service.compileAndTest(this.codereq).toPromise();
        this.cases = response;
        this.case_1 = this.cases[0];
        this.case_2 = this.cases[1];
        this.case_3 = this.cases[2];

        this.initializeCodeMirror();
      } else {
        this.case_1.message = 'Please select a programming language and enter code.';
        this.case_2.message = 'Please select a programming language and enter code.';
        this.case_3.message = 'Please select a programming language and enter code.';
      }
    } catch (error) {
      console.error('Error:', error);

      if (error.status === 400) {
        this.case_1.message = 'Bad Request';
        this.case_2.message = 'Bad Request';
        this.case_3.message = 'Bad Request';
      } else if (error.status === 500) {
        this.case_1.message = 'Internal Server Error';
        this.case_2.message = 'Internal Server Error';
        this.case_3.message = 'Internal Server Error';
      } else {
        this.case_1.message = 'An unexpected error occurred.';
        this.case_2.message = 'An unexpected error occurred.';
        this.case_3.message = 'An unexpected error occurred.';
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
    this.result.output = '';
  }

  clearAll() {
    localStorage.removeItem('javaEditorCode');
    localStorage.removeItem('cppEditorCode');
    localStorage.removeItem('cEditorCode');
    localStorage.removeItem('pythonEditorCode');
  }

}
