import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { QuestionSectionRoutingModule } from './question-section-routing.module';
import { QuestionSectionComponent } from './question-section.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { QuestionComponent } from './question/question.component';
import { CodingTestComponent } from './coding-test/coding-test.component';

import { ResizableDirective } from 'src/app/directives/resizabledirective';
import { SplitterModule } from 'primeng/splitter';




@NgModule({
  declarations: [QuestionSectionComponent, QuestionComponent, CodingTestComponent,ResizableDirective],
  imports: [
    CommonModule,
    HttpClientModule,
    FormsModule,

    QuestionSectionRoutingModule,
    SplitterModule

  ]
})
export class QuestionSectionModule { }
