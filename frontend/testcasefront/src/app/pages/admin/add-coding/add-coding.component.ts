import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { QuestionService } from 'src/app/services/question.service';
import Swal from 'sweetalert2';

declare var $: any;
declare var jQuery: any;
@Component({
  selector: 'app-add-coding',
  templateUrl: './add-coding.component.html',
  styleUrls: ['./add-coding.component.css']
})
export class AddCodingComponent implements OnInit {
addQuiz() {
throw new Error('Method not implemented.');
}

  @ViewChild('fileInput') fileInput: ElementRef;

  categories = []

  Data = {
    title: '',
    description: '',
    maxMarks: '',
    numberOfQuestions: '',
    active: true,
    category: {
      cid: ''
    },
  }

  constraintsElement:any;
  qId;
  qTitle;
  question = {
    heading: '',
    description: '',
    example1: '',
    example2: '',
    constraints: '',
    file: null,
  };
  message = '';
  fileName = 'Select File';

  constructor(private route: ActivatedRoute, private service: QuestionService, private router: Router) { }

  ngOnInit(): void {
    this.constraint();
    this.qId = this.route.snapshot.params.qid;
    this.qTitle = this.route.snapshot.params.title;
  }

  ngAfterViewInit() {
    this.fileInput.nativeElement;
  }

  constraint() {
    document.addEventListener('DOMContentLoaded', () => {
      this.constraintsElement = document.getElementById('constraints') as HTMLTextAreaElement;
      // Handle focus event
      this.constraintsElement.addEventListener('focus', () => {
        if (this.constraintsElement.value === '') {
          this.constraintsElement.value += '• ';
        }
      });
      // Handle keyup event
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
    });
  
  }

  browseFiles() {
    this.fileInput.nativeElement.click();
  }

  formSubmit() {
    if (this.question.heading.trim() == '' || this.question.heading == null || this.question.heading == undefined) {
      return;
    }
    if (this.question.description.trim() == '' || this.question.description == null || this.question.description == undefined) {
      return;
    }
    if (this.question.example1.trim() == '' || this.question.example1 == null || this.question.example1 == undefined) {
      return;
    }
    if (this.question.example2.trim() == '' || this.question.example2 == null || this.question.example2 == undefined) {
      return;
    }
    if (this.question.file == null || this.question.file == undefined || this.fileName == 'Select File') {
      this.message = 'Please choose a file for testcases.';
      return;
    }
    if (this.message != 'Please choose a file for testcases.') {
      this.message = '';
    }

}}

