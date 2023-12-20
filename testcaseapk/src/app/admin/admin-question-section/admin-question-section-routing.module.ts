import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminQuestionSectionComponent } from './admin-question-section.component';
import { McqQuestionsComponent } from './mcq-questions/mcq-questions.component';
import { CodingQuestionsComponent } from './coding-questions/coding-questions.component';
import { McqCreateComponent } from './mcq-questions/mcq-create/mcq-create.component';
import { McqUpdateComponent } from './mcq-questions/mcq-update/mcq-update.component';



const routes: Routes = [
  {path:"", component:AdminQuestionSectionComponent, children: [
    {path:'mcqquestions', component:McqQuestionsComponent},
    {path:'mcq-create', component:McqCreateComponent},
    {path:'mcq-update', component:McqUpdateComponent},
    {path:'codingquestions', component:CodingQuestionsComponent},
    {path:'', redirectTo:'mcqquestions',pathMatch:'full'}
  ]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminQuestionSectionRoutingModule { }
