import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { QuestionSectionComponent } from './question-section.component';
import { QuestionComponent } from './question/question.component';
import { CodingTestComponent } from './coding-test/coding-test.component';
import { TestComponent } from './test/test.component';


const routes: Routes = [
  {path:'', component:QuestionSectionComponent, children: [
    {path:'question', component:QuestionComponent},
    {path:'coding-test', component:CodingTestComponent},
    {path:'test', component:TestComponent},
    {path:'', redirectTo:'question',pathMatch:'full'}
  ]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class QuestionSectionRoutingModule { }
