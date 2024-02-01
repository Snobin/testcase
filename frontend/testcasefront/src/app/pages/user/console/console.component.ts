import { Component, ElementRef, HostListener, OnInit, ViewChild } from '@angular/core';
import { CodeRequest } from '../model/code-request';
import { CodeService } from 'src/app/services/code.service';
import { ActivatedRoute } from '@angular/router';
import { LocationStrategy } from '@angular/common';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { Case } from '../model/case';
import { Subject } from 'rxjs';
import { FullScreenService } from 'src/app/services/full-screen.service';


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


  @ViewChild('editor', { static: false }) editorTextarea: ElementRef;

  console: boolean = true;
  qId: any;
  case1OutputMessage: any;
  case2OutputMessage: any;
  private editor: any;
  userData = JSON.parse(localStorage.getItem('user'));
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
  cases: Case[] = [];

  questiondata;
  isOpen = false;
  braceLeft: string = '{';
  braceRight: string = '}';
  loading: boolean = true;
  submit: string = 'Submit';
  saveText: string = "<i class='bi bi-floppy2-fill'></i>";
  runText: string = "Run<i class='bi bi-play-fill'></i>";
  toast: boolean = false;

  constructor(private fullScreenService: FullScreenService,private service: CodeService, private route: ActivatedRoute, private locationst: LocationStrategy, private el: ElementRef) { }

  ngOnInit(): void {
    // if (localStorage.getItem('hasReloaded') == 'true') {
    //   localStorage.setItem('hasReloaded','false')
    //   window.location.reload();
    // }
    this.fullScreenService.requestFullScreen();
    this.clearAll();
    this.preventBackButton();

    this.qId = this.route.snapshot.params.qid;
    this.qnData(this.qId);
  }

  ngAfterViewInit() {
    this.initializeCodeMirror();
  }

  private cancelExecutionSubject = new Subject<void>();

  preventBackButton() {
    history.pushState(null, null, location.href);
    this.locationst.onPopState(() => {
      history.pushState(null, null, location.href)
    });
  }

  toggleOpen() {
    this.isOpen = !this.isOpen;
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
    this.cases = [this.cases[0], this.cases[1], this.cases[2]]; // Assuming you have cases initialized elsewhere
  }

  activateCase(index) {
    this.loading = false;
    this.cases.forEach((caseItem, i) => {
      caseItem.active = i === index;
    });
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
      // Update extraKeys
      this.editor.setOption("extraKeys", this.getExtraKeys());
    } else {
      // If the editor doesn't exist, create it
      this.editor = CodeMirror.fromTextArea(this.editorTextarea.nativeElement, {
        mode: this.getEditorMode(),
        theme: "dracula",
        lineNumbers: true,
        autoCloseBrackets: true,
        extraKeys: this.getExtraKeys(),
      });
      // Set the code to the CodeMirror editor
      this.editor.setValue(this.code);
    }
  }

  getEditorMode(): string {
    // Determine the CodeMirror mode based on the selected language
    if (this.selectedLanguage == 'java') {
      if (!localStorage.getItem(`${this.selectedLanguage}EditorCode`)) {
        this.code = "// This program prints Hello, world! \n \n class HelloWorld { \n\tpublic static void main(String[] args) {\n\t\tSystem.out.println(\"Hello, World!\"); \n\t}\n}";
      }
      return "text/x-java";
    } else if (this.selectedLanguage == 'cpp') {
      if (!localStorage.getItem(`${this.selectedLanguage}EditorCode`)) {
        this.code = "// This program prints Hello, world!\n\n#include <iostream>\n\nint main() {\n\tstd::cout << \"Hello World!\";\n\treturn 0;\n}"
      } else {
        this.code = localStorage.getItem(`${this.selectedLanguage}EditorCode`)
      }
      return "text/x-c++src";
    } else if (this.selectedLanguage == 'python') {
      if (!localStorage.getItem(`${this.selectedLanguage}EditorCode`)) {
        this.code = "# This program prints Hello, world!\n\nprint(\"Hello, world!\")";
      } else {
        this.code = localStorage.getItem(`${this.selectedLanguage}EditorCode`)
      }
      return "text/x-python";
    } else if (this.selectedLanguage == 'c') {
      if (!localStorage.getItem(`${this.selectedLanguage}EditorCode`)) {
        this.code = "// This program prints Hello, world! \n\n#include <stdio.h>\n\nint main() {\n\tprintf(\"Hello, World!\");\n\treturn 0;\n}";
      } else {
        this.code = localStorage.getItem(`${this.selectedLanguage}EditorCode`)
      }
      return "text/x-csrc";
    } else {
      if (!localStorage.getItem(`${this.selectedLanguage}EditorCode`)) {
        this.code = "// This program prints Hello, world! \n \n class HelloWorld { \n\tpublic static void main(String[] args) {\n\t\tSystem.out.println(\"Hello, World!\"); \n\t}\n}";
      } else {
        this.code = localStorage.getItem(`${this.selectedLanguage}EditorCode`)
      }
      return "text/x-java";
    }
  }

  getExtraKeys(): any {
    return {
      'Ctrl-Space': (cm) => {
        cm.showHint({
          hint: this.getHintFunction(),
          completeSingle: false, // Adjust as needed
        });
      },
      // Add other key bindings as needed
    };
  }

  getHintFunction(): any {
    switch (this.selectedLanguage) {
      case 'java':
        return CodeMirror.hint.java;
      case 'cpp':
        return CodeMirror.hint.cpp;
      case 'python':
        return CodeMirror.hint.python;
      case 'c':
        return CodeMirror.hint.c;
      // Add cases for other languages as needed
      default:
        // Use a default hint or set to null if no hint is needed
        return CodeMirror.hint.auto;
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

  //saving using Ctrl + S
  @HostListener('document:keydown', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent): void {
    if (event.ctrlKey && event.key === 's') {
      event.preventDefault(); // Prevent the default browser behavior (e.g., saving the page)
      this.save();
    }
  }

  save() {
    this.saveText = "<div class='spinner-border spinner-border-sm text-dark' role='status'></div>";
    // Save the current code to local storage before changing the language
    const localStorageKey = `${this.selectedLanguage}EditorCode`;
    localStorage.setItem(localStorageKey, this.editor.getValue());
    setTimeout(() => {
      this.saveText = "<i class='bi bi-floppy2-fill'></i>";
      this.toast = true;
      setTimeout(() => {
        this.toast = false;
      }, 2000);
    }, 500);
  }

  async executeCode(status: string) {
    this.cases = [];
    if (status == 'run' && this.runText == "Stop<i class='bi bi-stop-fill'></i>") {
      this.cancelExecution();
      for (let i = 0; i < 3; i++) {
        const c: any = {
          input: '',
          output: '',
          expectedOutput: '',
          processingTime: 0,
          message: '',
        };
        this.cases[i] = c;
        this.activateCase(0);
      }
      return;
    }
    if (status == 'Submit') {
      this.submit = "<div class='spinner-border spinner-border-sm text-light' role='status'></div>";
      this.loading = true;
      this.save();
      this.openOutput();
    }
    if (status == 'run') {
      this.runText = "Stop<i class='bi bi-stop-fill'></i>";
      this.loading = true;
      this.save();
      this.openOutput();
    }
    this.code = this.editor.getValue();
    try {
      if (this.selectedLanguage && this.code) {
        this.codereq.langId = this.selectedLanguage;
        this.codereq.code = this.code;
        this.codereq.qnId = this.questiondata.questionId;
        this.codereq.user = this.userData.username;
        this.codereq.status = status;
        const response = await this.service.compileAndTest(this.codereq, this.cancelExecutionSubject).toPromise();
        this.loading = false;
        if (status == 'Submit') {
          this.submit = "Submit";
          if (localStorage.getItem("codingQuestions")) {
            let codingQuestions = JSON.parse(localStorage.getItem("codingQuestions"));
            const index = codingQuestions.findIndex(q => q.questionId == this.qId);
            if (index != -1) {
              codingQuestions[index].status = 'Review';
            }
            localStorage.setItem('codingQuestions', JSON.stringify(codingQuestions));
          }
        }
        if (status == 'run') {
          this.runText = "Run<i class='bi bi-play-fill'></i>";
        }
        if (response && response.length) {
          for (let i = 0; i < response.length; i++) {
            this.cases[i] = response[i];

          }
        }
        this.activateCase(0);
        this.initializeCodeMirror();
      } else {
        this.handleError('Please select a programming language and enter code.');
      }
    } catch (error) {
      console.error('Error:', error);
      if (error.status === 400) {
        this.handleError('Bad Request');
      } else if (error.status === 500) {
        this.handleError('Internal Server Error');
      } else {
        this.handleError('An unexpected error occurred.');
      }
    }
  }

  cancelExecution() {
    this.cancelExecutionSubject.next();
  }

  handleError(errorMessage: string) {
    this.loading = false;
    this.submit = 'Submit';
    this.activateCase(0); // Assuming you want to activate the first case by default

    for (let i = 0; i < 3; i++) {
      this.cases[i].message = errorMessage;
    }
  }

  // activateCase(index) {
  //   this.cases.forEach((caseItem, i) => {
  //     caseItem.active = i === index;
  //   });
  // }

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
  @HostListener('document:visibilitychange', ['$event'])
  private handleVisibilityChange(event: Event): void {
  this.fullScreenService.onVisibilityChange(document.hidden);
}
 @HostListener('document:keydown', ['$event'])
 private handleKeyboard(event: KeyboardEvent): void {
  this.fullScreenService.onKeyDown(event);
}

}
