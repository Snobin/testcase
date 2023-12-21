import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthComponent } from './auth/auth.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { LoginComponent } from './login/login.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { QuestionSectionModule } from './exam/question-section/question-section.module';


@NgModule({
  declarations: [
    AppComponent,
    AuthComponent,
    LoginComponent,
    NotFoundComponent, 
    
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    QuestionSectionModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
