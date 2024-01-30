import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {

  sidebar: any = {
    "0": true,
    "1": false,
    "2": false,
    "3": false,
    "4": false,
    "5": false,
    "6": false,
    "7": false,
    "8": false,
  }

  constructor() { }

  ngOnInit(): void {
  }

  
  setFocus(selectedCategory: any) {
    for (let index = 0; index < 9; index++) {
      this.sidebar[index] = false;
      if (selectedCategory == index) {
        this.sidebar[selectedCategory] = true;
      }
    }
  }

}
