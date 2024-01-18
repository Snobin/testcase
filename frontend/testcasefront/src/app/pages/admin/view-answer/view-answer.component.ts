import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ResultService } from 'src/app/services/result.service';

export interface UserData {
  user: string;
  attempted: number;
  obtainedScore: number;
  correctAnswers: number;
  maxScore: string;
  totalQuestion: number;
}

@Component({
  selector: 'app-view-answer',
  templateUrl: './view-answer.component.html',
  styleUrls: ['./view-answer.component.css']
})
export class ViewAnswerComponent implements AfterViewInit, OnInit {
  displayedColumns: string[] = ['user', 'attempted', 'obtainedScore', 'correctAnswers', 'maxScore', 'totalQuestion'];
  dataSource: MatTableDataSource<UserData>;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private ans: ResultService) {
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
}
