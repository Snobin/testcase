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
<<<<<<< HEAD
    SplitterModule
=======

>>>>>>> 12c8a48aa658007b1ad7fae5ce44b3d89194b6cb
  ]
})
export class QuestionSectionModule { }
