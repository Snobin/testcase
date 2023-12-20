import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminQuestionSectionRoutingModule } from './admin-question-section-routing.module';
import { AdminQuestionSectionComponent } from './admin-question-section.component';
import { McqQuestionsComponent } from './mcq-questions/mcq-questions.component';
import { CodingQuestionsComponent } from './coding-questions/coding-questions.component';
import { FormsModule } from '@angular/forms';
import { McqCreateComponent } from './mcq-questions/mcq-create/mcq-create.component';
import { McqUpdateComponent } from './mcq-questions/mcq-update/mcq-update.component';



@NgModule({
  declarations: [AdminQuestionSectionComponent, McqQuestionsComponent, CodingQuestionsComponent, McqCreateComponent, McqUpdateComponent],
  imports: [
    CommonModule,
    AdminQuestionSectionRoutingModule,
    FormsModule
  ]
})
export class AdminQuestionSectionModule { }
