import { Component, ElementRef, OnInit, Renderer2, ViewChild } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { User } from 'src/app/model/user';
import { UserService } from 'src/app/services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  validationMessage: any;

  @ViewChild('fileInput') fileInput: ElementRef;
  userdata: User = new User();

  quizdata: any;

  fileContent: File;
  fileName: string;

  loginPage: boolean = true;
  signupPage: boolean = false;

  constructor(
    private userservice: UserService,
    private snack: MatSnackBar,
    private router: Router,
    private renderer: Renderer2,
    private el: ElementRef
  ) { }

  ngOnInit(): void {
    // this.userdata.role = 'USER'; // Set default role to 'USER'
    // No need to call toggleUserRole here, as the default is already set to 'USER'
    this.userdata.role = false;
    this.validationMessage = {};
    
  }

  formSubmit() {
    console.log(this.userdata);
    if (this.userdata.username == null || this.userdata.username == "") {
      this.snack.open("username is required ...", '', {
        duration: 3000,
        verticalPosition: 'top'
      });
      return;
    }
    this.userservice.addUser(this.userdata).subscribe(
      (data: any) => {
        if (data.body == 'Successfully Inserted') {
          Swal.fire("Success", 'User is Registered', 'success')
        } else {
          if (data.details) {
            this.clear();
            data.details.forEach((element) => {
              var key = Object.keys(element)[0];
              this.validationMessage[key] = element[key];
            });
          }
        }
      },
      (error) => {
        console.log(error);
        alert("error");
      }
    )
  }

  openLogin() {
    this.loginPage = true;
    this.signupPage = false;
  }

  openSignup() {
    this.loginPage = false;
    this.signupPage = true;
  }

  onFileChange(event: any): void {
    const fileList: FileList = event.target.files;
    if (fileList.length > 0) {
      const file: File = fileList[0];
      this.fileContent = file;
      this.fileName = file.name;
      // Set the file name for display if needed
      console.log(file);
      console.log(file);
      // Log the file to see if it's properly captured
    }
  }

  uploadFile() {
    this.userservice.uploadExcelFile(this.fileContent).subscribe(
      () => {
        this.router.navigate([`./admin/userlist`]);
        Swal.fire("Success", 'Users uploaded successfully', 'success')

      }, (error) => {
        console.log(error);

        Swal.fire('Error !!', 'error while adding New User', 'error');
      }

    );
  }

  clear() {
    this.userdata.email = null;
    this.userdata.password = null;
    this.userdata.firstName = null;
    this.userdata.lastName = null;
    this.userdata.name = null;
    this.userdata.role = false;
    this.userdata.username = null;
    this.userdata.phoneNumber = null;
  }
}
