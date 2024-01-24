import { Component, ElementRef, OnInit, Renderer2 } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { User } from 'src/app/model/user';
import { UserService } from 'src/app/services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  userdata: User=new User();

  constructor(private userservice:UserService,private snack:MatSnackBar,private renderer: Renderer2, private el: ElementRef) { }

  private slider: HTMLElement;
  private formSection: HTMLElement;
  currentView: 'form' | 'file' = 'form';

  toggleView(view: 'form' | 'file'): void {
    this.currentView = view;
  }


  ngAfterViewInit() {
    this.slider = this.el.nativeElement.querySelector('.slider');
    this.formSection = this.el.nativeElement.querySelector('.form-section');
  }

  onSignupClick() {
    this.renderer.addClass(this.slider, 'moveslider');
    this.renderer.addClass(this.formSection, 'form-section-move');
  }

  onLoginClick() {
    this.renderer.removeClass(this.slider, 'moveslider');
    this.renderer.removeClass(this.formSection, 'form-section-move');
  }

  ngOnInit(): void {
  }
  formSubmit() {
    console.log(this.userdata);
    if(this.userdata.username==null||this.userdata.username==""){
      this.snack.open("username is required ...",'',{
        duration:3000,
        verticalPosition:'top'
      });
      return;
    }
    this.userservice.addUser(this.userdata).subscribe(
      (data)=>{
        if (data.body == 'Successfully Inserted') {
          // this.router.navigate([`./login`]);
          Swal.fire("Success",'User is Registered','success')
        } else {
          Swal.fire('Error!', data.body,'error');
        }
      },
      (error)=>{
        console.log(error);
        alert("error");
      }
    )
  

  }

}
