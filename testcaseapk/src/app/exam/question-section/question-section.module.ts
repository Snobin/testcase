import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { QuestionSectionRoutingModule } from './question-section-routing.module';
import { QuestionSectionComponent } from './question-section.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: [QuestionSectionComponent],
  imports: [
    CommonModule,
    HttpClientModule,
    FormsModule,
    QuestionSectionRoutingModule
  ]
})
export class QuestionSectionModule { }
