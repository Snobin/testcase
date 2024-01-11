import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { QuestionService } from 'src/app/services/question.service';
import Swal from 'sweetalert2';
import { AddQuestion } from 'src/app/model/AddQuestion';

@Component({
  selector: 'app-add-code',
  templateUrl: './add-code.component.html',
  styleUrls: ['./add-code.component.css']
})
export class AddCodeComponent implements OnInit {
  @ViewChild('fileInput') fileInput: ElementRef;

  codeInput: AddQuestion =new AddQuestion();

  constraintsElement: HTMLTextAreaElement;
  qId;
  qTitle;
  fileName = 'Select File';
  message = '';

  constructor(
    private route: ActivatedRoute,
    private service: QuestionService,
    private router: Router,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.qId = this.route.snapshot.params.qid;
    this.qTitle = this.route.snapshot.params.title;
    this.constraintsElement = document.getElementById('constraints') as HTMLTextAreaElement;

    this.constraintsElement.addEventListener('focus', () => {
      if (this.constraintsElement.value === '') {
        this.constraintsElement.value += '• ';
      }
    });

    this.constraintsElement.addEventListener('keyup', (event) => {
      const keycode = event.keyCode ? event.keyCode : event.which;
      if (keycode === 13) {
        this.constraintsElement.value += '• ';
      }
      const txtval = this.constraintsElement.value;
      if (txtval.substr(txtval.length - 1) === '\n') {
        this.constraintsElement.value = txtval.substring(0, txtval.length - 1);
      }
    });
  }

  browseFiles() {
    this.fileInput.nativeElement.click();
  }

  formSubmit() {
    if (!this.codeInput.title || !this.codeInput.desc) {
      console.log("Validation failed");
      return;
    }

    const questionData: AddQuestion = {
      title: this.codeInput.title,
      desc: this.codeInput.desc,
      ex1input: this.codeInput.ex1input,
      ex1output: this.codeInput.ex1output,
      ex1explanation: this.codeInput.ex1explanation,
      ex2input: this.codeInput.ex2input,
      ex2output: this.codeInput.ex2output,
      ex2explanation: this.codeInput.ex2explanation,
      constraints: this.codeInput.constraints,
      file: this.codeInput.file,
      active:this.codeInput.active
    };

    this.service.addCodingQuestion(questionData).subscribe(
      (data: any) => {
        this.codeInput = {
          title: '',
          desc: '',
          ex1input: '',
          ex1output: '',
          ex1explanation: '',
          ex2input: '',
          ex2output: '',
          ex2explanation: '',
          constraints: [],
          file:null,
          active:null
        };
        this.fileName = 'Select File';
        Swal.fire('Success', 'Question Added', 'success');
      },
      (error) => {
        console.log(error)
        Swal.fire('Error', 'Error in adding question', 'error');
      }
    );
  }

  selectFile(event: any): void {
    if (event.target.files && event.target.files[0]) {
      const file: File = event.target.files[0];
      this.codeInput.file = file;
      this.fileName = file.name;
    } else {
      this.fileName = 'Select File';
    }
  }
}
