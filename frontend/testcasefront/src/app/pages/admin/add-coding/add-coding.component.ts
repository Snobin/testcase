import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
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

  qId;
  qTitle;
  question = {
    heading: '',
    description: '',
    example1: '',
    example2: '',
    constraints: '',
    file: ''
  };
  
  currentFile?: File;
  progress = 0;
  message = '';

  fileName = 'Select File';

  constructor(private route: ActivatedRoute, private service: QuestionService, private router: Router) { }

  ngOnInit(): void {
    this.constraint();
    this.qId = this.route.snapshot.params.qid;
    this.qTitle = this.route.snapshot.params.title;
  }

  constraint() {
    document.addEventListener('DOMContentLoaded', () => {
      const constraintsElement = document.getElementById('constraints') as HTMLTextAreaElement;
    
      // Handle focus event
      constraintsElement.addEventListener('focus', () => {
        if (constraintsElement.value === '') {
          constraintsElement.value += '• ';
        }
      });
    
      // Handle keyup event
      constraintsElement.addEventListener('keyup', (event) => {
        const keycode = event.keyCode ? event.keyCode : event.which;
    
        if (keycode === 13) {
          constraintsElement.value += '• ';
        }
    
        const txtval = constraintsElement.value;
    
        if (txtval.substr(txtval.length - 1) === '\n') {
          constraintsElement.value = txtval.substring(0, txtval.length - 1);
        }
      });
    });
    
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
    if (this.question.file.trim() == '' || this.question.file == null || this.question.file == undefined) {
      return;
    }
    // form submit
    this.service.addQuestion(this.question).subscribe(
      (data: any) => {
        this.question.heading = '';
        this.question.description = '';
        this.question.example1 = '';
        this.question.example2 = '';
        this.question.constraints = '';
        this.question.file = '';
        Swal.fire('Success', 'Question Added', 'success');
      }, (error) => {
        Swal.fire('Error', 'Error in adding question', 'error');
      }
    )
  }


  selectFile(event: any): void {
    if (event.target.files && event.target.files[0]) {
      const file: File = event.target.files[0];
      this.currentFile = file;
      this.fileName = this.currentFile.name;
    } else {
      this.fileName = 'Select File';
    }
  }

  upload(): void {
    this.progress = 0;
    this.message = "";

    if (this.currentFile) {
      this.service.upload(this.currentFile).subscribe(
        (event: any) => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progress = Math.round(100 * event.loaded / event.total);
          } else if (event instanceof HttpResponse) {
            this.message = event.body.message;
          }
        },
        (err: any) => {
          console.log(err);
          this.progress = 0;

          if (err.error && err.error.message) {
            this.message = err.error.message;
          } else {
            this.message = 'Could not upload the file!';
          }

          this.currentFile = undefined;
        });
    }

  }

}

