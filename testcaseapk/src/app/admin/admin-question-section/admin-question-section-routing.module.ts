import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminQuestionSectionComponent } from './admin-question-section.component';
import { McqQuestionsComponent } from './mcq-questions/mcq-questions.component';
import { CodingQuestionsComponent } from './coding-questions/coding-questions.component';



const routes: Routes = [
  {path:"", component:AdminQuestionSectionComponent, children: [
    {path:'mcqquestions', component:McqQuestionsComponent},
    {path:'codingquestions', component:CodingQuestionsComponent},
    {path:'', redirectTo:'mcqquestions',pathMatch:'full'}
  ]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminQuestionSectionRoutingModule { }
