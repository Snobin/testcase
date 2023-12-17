import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { QuestionSectionRoutingModule } from './question-section-routing.module';
import { QuestionSectionComponent } from './question-section.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { QuestionComponent } from './question/question.component';
import { CodingTestComponent } from './coding-test/coding-test.component';
import { AngularSplitModule } from 'angular-split';


@NgModule({
  declarations: [QuestionSectionComponent, QuestionComponent, CodingTestComponent],
  imports: [
    CommonModule,
    HttpClientModule,
    FormsModule,
    AngularSplitModule,
    QuestionSectionRoutingModule
  ]
})
export class QuestionSectionModule { }
