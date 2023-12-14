import { Component, OnInit } from '@angular/core';
import { McqQuestions } from '../model/mcq-questions';
import { Router } from '@angular/router';
import { ServiceService } from 'src/app/service/service.service';

declare var $: any;
declare var jQuery: any;

@Component({
  selector: 'app-mcq-questions',
  templateUrl: './mcq-questions.component.html',
  styleUrls: ['./mcq-questions.component.css']
})
export class McqQuestionsComponent implements OnInit {

  static obj: McqQuestionsComponent;
  slect2: string = "";
  username: string = "";
  mcqObj: McqQuestions= new McqQuestions();
  selectedUserIdForDelete: any;
  userDatatable: any;
  dataTableUrl: string;
  coloumDropdown: boolean = false;
  securityUser: any = {};
  initialHead: HeaderConfig[] = [
    { name: "Question Number", isSelected: false },
    { name: "Question Type", isSelected: false },
    { name: "Question", isSelected: false },
    { name: "Option A", isSelected: false },
    { name: "Option B", isSelected: false },
    { name: "Option C", isSelected:false},
    { name: "Option D", isSelected: false },
    { name: "Answer", isSelected: false },
    { name: "Score", isSelected: false },
    { name: "status", isSelected: false },
  ];
 
  statusCountList: StatusCountModel = new StatusCountModel();
  // token: any = {};
  constructor(private mcqService: ServiceService,  private router: Router,) {
    this.dataTableUrl = "http://localhost:8058/MCQ/search";
    // this.token = this.commonServiceProvider.getUserDetailsWithToken();
  }
  ngOnInit(): void {
    McqQuestionsComponent.obj = this;
   this. getUsers();
  }
   //
   getUsers() {
   
    this.userDatatable = $('#userList').DataTable({
      "bProcessing": true,
      "bDeferRender": true,
      "ordering": false,
      bAutoWidth: false,
      bServerSide: true,
     
      sAjaxSource: this.dataTableUrl,
     
      "fnServerParams": function (aoData) {
        var dataString = McqQuestionsComponent.obj.getSearchInputs();
        aoData.push({ name: "searchParam", value: dataString });
      },
      "fnServerData": (sSource, aoData, fnCallback, oSettings) => {
        oSettings.jqXHR = $.ajax({
          "dataType": 'json',
          "type": "GET",
          "url": sSource,
          "data": aoData,
          "beforeSend": (xhr) => {
            
          },
          "success": (data) => {
            //err1
            // this.libraryService.setStatusCount(this.statusCountList, data.countByStatus);
            fnCallback(data);
          },
          // "success": fnCallback,
          "error": (e) => {
            if (e.status == "403" || e.status == "401") {
              // this.commonServiceProvider.GoToErrorHandling(e);
            }
          }
         
        });
      },
      // "bSort": false,
      "sDom": "<rt><'row border-top pt-2'<'col-sm-12 col-md-5'l><'col-sm-12 col-md-7'p>>",
      "aoColumns": [
        { "mDataProp": "questionNo", "bSortable": false },
        { "mDataProp": "questionId", "bSortable": false },
        { "mDataProp": "question", "bSortable": false, },
        { "mDataProp": "optionA", "bSortable": false },
        { "mDataProp": "optionB", "bSortable": false },
        {"mDataProp":"optionC","bSortable":false},
        {"mDataProp":"optionD","bSortable":false},
        {"mDataProp":"answers","bSortable":false},
        {"mDataProp":"score","bSortable":false},
        {
          "mDataProp": "status", "bSortable": false,
          "mRender": function (data) {

            if (data == 'PROCESSED') {
              return '<span class="badge bg-primary text-white ipsh-badge-pending">Pending Approval</span>';
          } else if (data == 'DELETE') {
              return '<span class="badge bg-warning text-dark ipsh-badge-pending">Pending Approval</span>';
          } else if (data == 'VERIFIED') {
              return '<span class="badge bg-success text-white ipsh-badge-approve">Approved</span>';
          } else if (data == 'REJECT') {
              return '<span class="badge bg-danger text-white ipsh-badge-reject">Rejected</span>';
          } else if (data == 'DELETED') {
              return '<span class="badge bg-secondary text-white ipsh-badge-delete">Deleted</span>';
          } else if (data == 'RECEIVED') {
              return '<span class="badge bg-success text-white ipsh-badge-approve">Processed</span>';
          }
            return data;
          }
        },
      ]
    });
    // this.getCheckBoxesModal();
    $('#userList tbody').on('click', 'tr', (event) => {
      // console.log(event);
      $(event.currentTarget).toggleClass('selected');
    });
  }
  addColumn() {
    this.coloumDropdown = !this.coloumDropdown;
  }

  getSearchInputs() {
    let Search: McqQuestions = new McqQuestions();
    Search.questionNo = $('#questionNo').val();
    Search.questionId = $('#questionId').val();
    Search.status = $('#idStatus').val();

    // Check for null or undefined values and set them to empty strings
    if (Search.questionNo == null || Search.questionNo == undefined) {
      Search.questionNo = '';
    }
    if (Search.status == null || Search.status == undefined) {
      Search.status = '';
    }
    if (Search.questionId == null || Search.questionId == undefined) {
      Search.questionId = '';
    }
    // Check if there are any non-empty fields in the guestSearch object
    if (Object.values(Search).length>0) {
      return JSON.stringify(Search);
    }
    return "";
}
  showHideColoum(values: any, data) {
    var newOrder = this.userDatatable.colReorder.order();
    var col = parseInt(values.currentTarget.value);
    var index = jQuery.inArray(col, newOrder);
    if (data.isSelected == true) {
      this.userDatatable.column(index).visible(true);
    } else {
      this.userDatatable.column(index).visible(false);
    }
  }
  refreshByStatus(status: string) {
    $("#idStatus").val(status).trigger("change");
    this.search();
  }
  search() {
    this.slect2 = $("#id-userType").val();
    this.userDatatable.draw();
  }
  clear() {
    $('#bookTitle').val('');
    $('#bookId').val('');
    $('#guestName').val('');
    $('#borrowingDate').val('');
    $('#idStatus').val("").trigger("change");
    $('#bookCategory').val("").trigger("change");
    $('#price').val('');
    this.userDatatable.draw();
    //  this.savePageDataWithencryption();
  }
  get(label) {
    return  label;
  }
  createUser() {
    this.router.navigate(['/create']);
  }
  updateUser() {
    // if (this.userDatatable.rows('.selected').data().length == 0) {
    //   //err1
    //   this.libraryService.ShowToasts('', '', "Please Select a Record To Update.", 'Warning');
    // } else if (this.userDatatable.rows('.selected').data().length > 1) {
    //   //err1
    //   this.libraryService.ShowToasts('', '', "Multiple Record You Cannot Update.", 'Warning');
    // } else
    //     console.log(this.libraryObj)
    //     console.log(this.userDatatable.rows('.selected').data()[0].userType);
    //     let bookTitle = window.btoa(this.userDatatable.rows('.selected').data()[0].bookTitle);
    //     let bookId = window.btoa(this.userDatatable.rows('.selected').data()[0].bookId);
    //     console.log("landing"+bookId);
    //     this.router.navigate(['./update',bookTitle,bookId]);
      }
      verify() {
        // if (this.userDatatable.rows('.selected').data().length == 0) {
        //   //err1
        //   this.libraryService.ShowToasts('', '', "Please Select a Record To Update.", 'Warning');
        // } else if (this.userDatatable.rows('.selected').data().length > 1) {
        //   //err1
        //   this.libraryService.ShowToasts('', '', "Multiple Record You Cannot Update.", 'Warning');
        // } else
        //     console.log(this.libraryObj)
        //     console.log(this.userDatatable.rows('.selected').data()[0].userType);
        //     let bookTitle = window.btoa(this.userDatatable.rows('.selected').data()[0].bookTitle);
        //     let bookId = window.btoa(this.userDatatable.rows('.selected').data()[0].bookId);
        //     console.log("landing"+bookId);
        //     this.router.navigate(['./verify',bookTitle,bookId]);
          }
          delete() {
            // if (this.userDatatable.rows('.selected').data().length == 0) {
            //   //err1
            //   this.libraryService.ShowToasts('', '', "Please Select a Record To Update.", 'Warning');
            // } else if (this.userDatatable.rows('.selected').data().length > 1) {
            //   //err1
            //   this.libraryService.ShowToasts('', '', "Multiple Record You Cannot Update.", 'Warning');
            // } else
            //     console.log(this.libraryObj)
            //     console.log(this.userDatatable.rows('.selected').data()[0].userType);
            //     let bookTitle = window.btoa(this.userDatatable.rows('.selected').data()[0].bookTitle);
            //     let bookId = window.btoa(this.userDatatable.rows('.selected').data()[0].bookId);
            //     console.log("landing"+bookId);
            //     this.router.navigate(['./delete',bookTitle,bookId]);
               }
    }
export class HeaderConfig {
  name: string;
  isSelected: boolean;
}
export class StatusCountModel {
  delete: number;
  deleted: number;
  processd: number;
  totalStatus: number;
  verified: number;
  reprocess: number;
  constructor() {
    this.delete = 0;
    this.deleted = 0;
    this.processd = 0;
    this.totalStatus = 0;
    this.verified = 0;
    this.reprocess = 0;
  }
}

