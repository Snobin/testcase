import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { LandingComponent } from './landing/landing.component';


const routes: Routes = [
  {path: 'login', component:LoginComponent},
  {path: 'landing', component:LandingComponent},
  {path: 'exam', loadChildren: () => import(`./exam/exam.module`).then(m => m.ExamModule) },
  {path: '', redirectTo:'login', pathMatch: 'full'},
  {path: '404', component: NotFoundComponent},
  {path: '**', redirectTo: '/404'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
