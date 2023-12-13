import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { InstructionsComponent } from './instructions/instructions.component';
import { ExamComponent } from './exam.component';


const routes: Routes = [
  {path:'', component:ExamComponent, children: [
    {path: 'instructions', component:InstructionsComponent},
    {path: 'question-section', loadChildren: () => import(`./question-section/question-section.module`).then(m => m.QuestionSectionModule) }
  ]},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ExamRoutingModule { }
