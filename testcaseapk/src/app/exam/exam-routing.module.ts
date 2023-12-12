import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { QuestionsComponent } from './questions/questions.component';
import { InstructionsComponent } from './instructions/instructions.component';
import { ExamComponent } from './exam.component';


const routes: Routes = [
  {path:'', component:ExamComponent, children: [
    {path:'questions/:id', component:QuestionsComponent},
    {path: 'instructions', component:InstructionsComponent}
  ]},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ExamRoutingModule { }
