import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { QuestionService } from 'src/app/services/question.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-code',
  templateUrl: './add-code.component.html',
  styleUrls: ['./add-code.component.css']
})
export class AddCodeComponent implements OnInit {
  @ViewChild('fileInput') fileInput: ElementRef;
  constraintsElement: HTMLTextAreaElement;
  qId;
  qTitle;
  fileName = 'Select File';
  message = '';

  // Individual form controls
  heading = '';
  description = '';
  input = '';
  output = '';
  explanation = '';
  constraints = '';
  file: File | null = null;

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

    // Handle focus and keyup events for constraints
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
    if (!this.heading || !this.description) {
      // Handle validation here as needed
      console.log("hifeuyufe")
      return;
    }
    console.log("hello")


    // Form submit
    const questionData = {
      heading: this.heading,
      description: this.description,
      examples: {
        input: this.input,
        output: this.output,
        explanation: this.explanation,
      },
      constraints: this.constraints,
      file: this.file,
    };

    this.service.addCodingQuestion(questionData).subscribe(
      (data: any) => {

        // Reset individual form controls
        this.heading = '';
        this.description = '';
        this.input = '';
        this.output = '';
        this.explanation = '';
        this.constraints = '';
        this.file = null;
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
      this.file = event.target.files[0];
      this.fileName = this.file.name;
    } else {
      this.fileName = 'Select File';
    }
  }
}
