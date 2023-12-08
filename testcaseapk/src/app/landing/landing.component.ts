import { Component, OnInit } from '@angular/core';
import { ServiceService } from '../service/service.service';

@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.css']
})
export class LandingComponent implements OnInit {



  ngOnInit(): void {
  }
  
  testCases: string[] = [];
  result: any;
  code: string = '';

  constructor(private apiService: ServiceService) { }

  compileAndTest() {
    // Call your backend API to compile and test the code
    this.apiService.compileAndTestCode(this.code).subscribe(
      (response) => {
        this.result = response;
      },
      (error) => {
        console.error('Error compiling and testing code:', error);
        this.result = { success: false, errorMessage: 'Failed to communicate with the server.' };
      }
    );
  }

}
