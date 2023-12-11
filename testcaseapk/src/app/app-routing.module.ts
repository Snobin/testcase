import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LandingComponent } from './landing/landing.component';
import { LoginComponent } from './login/login.component';


const routes: Routes = [
  {path: 'landing', component:LandingComponent},
  {path: 'login', component:LoginComponent},
  {path: '', redirectTo:'welcome',pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
