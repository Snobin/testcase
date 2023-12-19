import { Component, OnInit } from '@angular/core';
import { McqQuestions } from '../../model/mcq-questions';
import { Router } from '@angular/router';
import { ServiceService } from 'src/app/service/service.service';



declare var $: any;
declare var jQuery: any;

@Component({
  selector: 'app-mcq-create',
  templateUrl: './mcq-create.component.html',
  styleUrls: ['./mcq-create.component.css']
})
export class McqCreateComponent implements OnInit {

  validationMessage:any;
  mcq: McqQuestions=new McqQuestions();
  fileList3: any;
  constructor(private mcqService: ServiceService, private router:Router) { }
  ngOnInit(): void {
    this.validationMessage={};
  }
  addquestiondetail(){
    this.validationMessage={};
    let questionType =$('#questionId').val();;
    this.mcq.questionId=questionType;
    this.mcqService.addquestion(this.mcq).subscribe((data:any) =>{
      if (data.code.toLowerCase() == "success") {
        this.clear();
      } else {
        if (data.details) {
          data.details.forEach(element => {
            var key = Object.keys(element)[0];
            this.validationMessage[key] = element[key];
          });
        // console.log(this.validationMessage);
        }
      }
      console.log(data);
      // this.libraryList();
    },error=>console.log(error));
  }
  onFileChange(event) {
    console.log("event innitiated")
    var filePath = $("#idfile").val();
    $(".tempFileLabel").text(filePath);

    this.fileList3 = event.target.files;
    if (this.fileList3[0].size > 1048576) {
      // this.commonServiceProvider.ShowToasts('', '', "File Size Exceeds 1 MB Please Upload Another File.", 'Warning');
      // this.toastr.warning("File Size Exceeds 1 MB Please upload another file", '', { positionClass: 'toast-bottom-right' });
      this.fileList3 = null;
    } else {
      console.log("File exist and in correct size")
      let reader = new FileReader();
      let file = this.fileList3[0];
      console.log(file)
      reader.readAsBinaryString(file);
      var byteString;
      reader.onload = (file) => {
        console.log("in the load function")
        let temp = btoa(reader.result.toString());
       if(temp =="" || temp ==null || temp == undefined){
        console.log("File is empty")
        // this.commonServiceProvider.ShowToasts('', '', "File Is Empty! Please Upload Another File.", 'Warning');
        // this.toastr.warning("File is empty!!. Please upload another file", '', { positionClass: 'toast-bottom-right' });
       }else{
        console.log("File assigned successufully")
        this.mcq.file = temp;
        console.log(this.mcq.file)
        this.mcq.fileName = this.fileList3[0].name;
        if(this.fileList3[0].name.length>50){
          $("#fileUploader").text(this.fileList3[0].name.substring(0,30)+ "........");
        }else{
          $("#fileUploader").text(this.fileList3[0].name);
        }
       }
      };
    }
  }
  clear() {
    $('#bookTitle').val('');
    $('#bookId').val('');
    $('#memberName').val('');
    $('#memberContact').val('');
    $('#borrowingDate').val('');
    $('#idStatus').val("").trigger("change");
    $('#bookCategory').val("").trigger("change");
  }
  questionList(){
    this.router.navigate(['admin/admin-question-section/mcqquestions']);
  }
  onSubmit(){
  }

}
