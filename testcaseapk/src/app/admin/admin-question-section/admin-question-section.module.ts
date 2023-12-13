import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminQuestionSectionRoutingModule } from './admin-question-section-routing.module';
import { AdminQuestionSectionComponent } from './admin-question-section.component';
import { McqQuestionsComponent } from './mcq-questions/mcq-questions.component';
import { CodingQuestionsComponent } from './coding-questions/coding-questions.component';



@NgModule({
  declarations: [AdminQuestionSectionComponent, McqQuestionsComponent, CodingQuestionsComponent],
  imports: [
    CommonModule,
    AdminQuestionSectionRoutingModule
  ]
})
export class AdminQuestionSectionModule { }
