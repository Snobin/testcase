import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-add-coding',
  templateUrl: './add-coding.component.html',
  styleUrls: ['./add-coding.component.css']
})
export class AddCodingComponent implements OnInit {
qTitle: any;
formSubmit() {
throw new Error('Method not implemented.');
}
  codingData: any = {};
question: any;
  constructor() { }

  ngOnInit(): void {
  }
  addCodingQuestion() {
    // Implement the logic to add coding question
    console.log(this.codingData);
  }

  onFileChange(event: any) {
    // Implement logic to handle file change (e.g., uploading test cases)
    const file = event.target.files[0];
    console.log(file);
  }

}
