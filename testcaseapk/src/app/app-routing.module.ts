import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LandingComponent } from './landing/landing.component';
import { LoginComponent } from './login/login.component';
import { InstructionsComponent } from './instructions/instructions.component';


const routes: Routes = [
  {path: 'landing', component:LandingComponent},
  {path: 'login', component:LoginComponent},
  {path: 'instructions', component:InstructionsComponent},
  {path: '', redirectTo:'login',pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
