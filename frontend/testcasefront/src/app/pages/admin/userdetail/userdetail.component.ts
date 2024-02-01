import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';

import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { Router } from '@angular/router';
import { ResultService } from 'src/app/services/result.service';
import { LoginService } from 'src/app/services/login.service';

export interface UserData {
  firstName: String,
  lastName: String,
  email: String,
  phoneNumber: String,
  username: String,
  role: String
}


@Component({
  selector: 'app-userdetail',
  templateUrl: './userdetail.component.html',
  styleUrls: ['./userdetail.component.css']
})
export class UserdetailComponent implements OnInit {

  selectedRows: any[] = [];

  displayedColumns: string[] = ['firstName', 'lastName', 'email', 'phoneNumber', 'username','role'];
  dataSource: MatTableDataSource<UserData>;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private ans: ResultService,private router:Router,private list: LoginService) { 
    this.dataSource = new MatTableDataSource<UserData>([]);
  }

  ngOnInit(): void {
    this.getData();
  }
  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  getData() {
    this.list.getData().subscribe(
      (data: any) => {
        this.dataSource.data = data;
      },
      (error) => {
        // Handle error
      }
    );
  }
  create(){
    this.router.navigate(['admin/users']);
  }
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
  isSelected(row: any): boolean {
    return this.selectedRows.includes(row);
  }

  handleSingleClick(row: any): void {
    // Toggle the selection of the row
    const index = this.selectedRows.indexOf(row);
    if (index === -1) {
      // If not already selected, add to the array
      this.selectedRows.push(row);
    } else {
      // If already selected, remove from the array
      this.selectedRows.splice(index, 1);
    }
  }
  deleteSelectedRows(): void {
    if (this.selectedRows.length > 0) {
      const selectedUsers = this.selectedRows.map((row) => row.user);
      console.log(selectedUsers);
  
      this.ans.deleteUsers(selectedUsers).subscribe(
        (data: any) => {
          console.log(data);
          this.getData();
          this.selectedRows = []; // Clear selected rows after deletion
        },
        (error) => {
          // Handle error
          console.log(error);
        }
      );
    }
  }

}
