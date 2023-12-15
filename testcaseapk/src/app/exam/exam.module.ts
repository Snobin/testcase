import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ExamRoutingModule } from './exam-routing.module';
import { ExamComponent } from './exam.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { InstructionsComponent } from './instructions/instructions.component';





@NgModule({
  declarations: [

    ExamComponent,
    InstructionsComponent,

  
  ],
  imports: [
    CommonModule,
   
    HttpClientModule,
    ExamRoutingModule,
    FormsModule,
  ]
})
export class ExamModule { }
