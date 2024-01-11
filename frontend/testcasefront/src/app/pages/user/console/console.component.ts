import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { CodeRequest } from '../model/code-request';
import { CodeService } from 'src/app/services/code.service';
import { AddQuestion } from 'src/app/model/AddQuestion';

declare var CodeMirror: any;
@Component({
  selector: 'app-console',
  templateUrl: './console.component.html',
  styleUrls: ['./console.component.css']
})
export class ConsoleComponent implements OnInit {


  @ViewChild('editor', { static: false }) editorTextarea: ElementRef;
  @ViewChild('output', { static: false }) outputTextarea: ElementRef;

  private editor: any;
  private output: any;
  selectedLanguage: string = 'java';
  testCases: string[] = [];
  result: any = { success: true, output: '' };
  code: string = "// This program prints Hello, world! \n \n class HelloWorld { \n\tpublic static void main(String[] args) {\n\t\tSystem.out.println('Hello, World!'); \n\t}\n}";
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

  constructor(private service: CodeService) { }

  ngOnInit(): void {
    this.clearAll();
    this.qnData(1);
  }

  ngAfterViewInit() {
    this.initializeCodeMirror();
    this.initializeCodeMirrorOutput();
  }

  qnData(qid){
    this.service.questionReq(qid).subscribe(
      (data:any)=>{
        this.questiondata=data;
      },
      (error)=>{
        console.log(error)
      }
    )
  }

  initializeCodeMirrorOutput() {
    if (this.output) {
      // If the output already exists, just set the new mode and theme
      this.output.setOption("mode", "text/x-java");
      // Set the code value
      this.output.setValue(this.result.output);
    } else {
      // If the output doesn't exist, create it
      this.output = CodeMirror.fromTextArea(this.outputTextarea.nativeElement, {
        mode: "text/x-java",
        theme: "dracula",
      });
      this.output.setSize(window, 173);
      // Set the code to the CodeMirror output
      this.output.setValue('');
    }
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
    this.initializeCodeMirrorOutput();
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
    this.code = this.editor.getValue();
    try {
      if (this.selectedLanguage && this.code) {
        this.codereq.langId = this.selectedLanguage;
        this.codereq.code = this.code;

        const response = await this.service.compileAndTest(this.codereq).toPromise();

        this.result.output = response.output;
        this.executionTime = response.processingTime;
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
