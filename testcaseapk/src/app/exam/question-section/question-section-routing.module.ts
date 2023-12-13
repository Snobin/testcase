import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { QuestionSectionComponent } from './question-section.component';
import { QuestionComponent } from './question/question.component';
import { CodingTestComponent } from './coding-test/coding-test.component';


const routes: Routes = [
  {path:'', component:QuestionSectionComponent, children: [
    {path:'question/:id', component:QuestionComponent},
    {path:'coding-test/:id', component:CodingTestComponent},
    {path:'', redirectTo:'question/:id',pathMatch:'full'}
  ]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class QuestionSectionRoutingModule { }
