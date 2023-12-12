import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { QuestionSectionComponent } from './question-section.component';


const routes: Routes = [
  {path:'', component:QuestionSectionComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class QuestionSectionRoutingModule { }
