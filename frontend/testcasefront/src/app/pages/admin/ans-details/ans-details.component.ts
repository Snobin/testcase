import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ResultService } from 'src/app/services/result.service';

@Component({
  selector: 'app-ans-details',
  templateUrl: './ans-details.component.html',
  styleUrls: ['./ans-details.component.css']
})
export class AnsDetailsComponent implements OnInit {
  user: any;
  dataa;
  MCQ: boolean = true;
  Code: boolean = true;
  constructor(private ans: ResultService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.user = params.userId;
      this.ans.getDataByUser(this.user).subscribe(
        (data: any) => {
          this.dataa = data;
          console.log(this.dataa);

        },
        (error) => {

        }
      )

    });

  }
}
