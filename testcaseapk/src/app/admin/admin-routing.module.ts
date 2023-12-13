import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminComponent } from './admin.component';


const routes: Routes = [
  {path:"", component:AdminComponent, children: [
    {path:'admin-question-section', loadChildren: () => import(`./admin-question-section/admin-question-section.module`).then(m => m.AdminQuestionSectionModule) },
    {path:'', redirectTo:'admin-question-section',pathMatch:'full'}
  ]},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
