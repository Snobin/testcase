import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { ResultService } from 'src/app/services/result.service';

export interface UserData {
  user: string;
  attempted: number;
  obtainedScore: number;
  correctAnswers: number;
  maxScore: string;
  totalQuestion: number;
  codingPercentage:number;
  status:String;
}

@Component({
  selector: 'app-view-answer',
  templateUrl: './view-answer.component.html',
  styleUrls: ['./view-answer.component.css']
})
export class ViewAnswerComponent implements AfterViewInit, OnInit {
  displayedColumns: string[] = ['user', 'attempted', 'obtainedScore', 'correctAnswers', 'maxScore', 'totalQuestion','codingPercentage','status'];
  dataSource: MatTableDataSource<UserData>;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  // selectedRow: any;
  selectedRows: any[] = [];

  constructor(private ans: ResultService,private router:Router) {
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
    this.ans.getData().subscribe(
      (data: any) => {
        this.dataSource.data = data;
      },
      (error) => {
        // Handle error
      }
    );
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  navigateToDetails(row: UserData): void {
    // Assuming you have a route named 'details' that takes a parameter 'userId'
    this.router.navigate(['admin/details', row.user]);
  }

  // handleDoubleClick(row: any): void {
  //   this.selectedRow = row; // Set the selected row
  //   console.log("haii")
  // }

  // handleSingleClick(row: any): void {
  //   this.selectedRow = row; // Set the row as selected on single click
  // }
  isSelected(row: any): boolean {
    return this.selectedRows.includes(row);
  }

  handleDoubleClick(row: any): void {
    // Navigate to details page on double click
    this.navigateToDetails(row);
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