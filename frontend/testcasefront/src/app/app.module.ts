import { BrowserModule } from '@angular/platform-browser';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSnackBarModule } from '@angular/material/snack-bar';

import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { NavbarComponent } from './components/navbar/navbar.component';
import { SignupComponent } from './pages/signup/signup.component';
import { LoginComponent } from './pages/login/login.component';
import { DashboardComponent } from './pages/admin/dashboard/dashboard.component';
import { HomeComponent } from './pages/home/home.component';
import { UserDashboardComponent } from './pages/user/user-dashboard/user-dashboard.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { MatListModule } from '@angular/material/list';
import { MatCardModule } from '@angular/material/card';
import { SidebarComponent } from './pages/admin/sidebar/sidebar.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { authInterceptorProviders } from './services/auth.interceptor';
import { ViewCategoriesComponent } from './pages/admin/view-categories/view-categories.component';
import { AddCategoriesComponent } from './pages/admin/add-categories/add-categories.component';
import { ViewQuizzesComponent } from './pages/admin/view-quizzes/view-quizzes.component';
import { AddQuizComponent } from './pages/admin/add-quiz/add-quiz.component';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatSelectModule } from '@angular/material/select';
import { UpdateQuizComponent } from './pages/admin/update-quiz/update-quiz.component';
import { ViewQuizQuestionsComponent } from './pages/admin/view-quiz-questions/view-quiz-questions.component';
import { AddQuestionComponent } from './pages/admin/add-question/add-question.component';
import { SidebaruserComponent } from './pages/user/sidebaruser/sidebaruser.component';
import { LoadQuizComponent } from './pages/user/load-quiz/load-quiz.component';
import { InstructionsComponent } from './pages/user/instructions/instructions.component';
import { StartComponent } from './pages/user/start/start.component';
import {MatRadioModule} from '@angular/material/radio';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import { AddCodingComponent } from './pages/admin/add-coding/add-coding.component';
import { ViewAnswerComponent } from './pages/admin/view-answer/view-answer.component';
import { ConsoleComponent } from './pages/user/console/console.component';
import { SplitterModule } from 'primeng/splitter';
import { AddCodeComponent } from './pages/admin/add-code/add-code.component';
import { ViewCodingComponent } from './pages/admin/view-coding/view-coding.component';
import { ReactiveFormsModule } from '@angular/forms';
import { UpdatecodeComponent } from './pages/admin/updatecode/updatecode.component';
import {MatTableModule} from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { McqComponent } from './pages/user/mcq/mcq.component';
import { AnsDetailsComponent } from './pages/admin/ans-details/ans-details.component';
import { FinalComponent } from './pages/user/final/final.component';
import { UsersComponent } from './pages/admin/users/users.component';
import { FullScreenService } from './services/full-screen.service';



@NgModule({
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  declarations: [
    AppComponent,
    NavbarComponent,
    SignupComponent,
    LoginComponent,
    DashboardComponent,
    HomeComponent,
    UserDashboardComponent,
    ProfileComponent,
    SidebarComponent,
    ViewCategoriesComponent,
    AddCategoriesComponent,
    ViewQuizzesComponent,
    AddQuizComponent,
    UpdateQuizComponent,
    ViewQuizQuestionsComponent,
    AddQuestionComponent,
    SidebaruserComponent,
    LoadQuizComponent,
    InstructionsComponent,
    StartComponent,
    AddCodingComponent,
    ViewAnswerComponent,
    ConsoleComponent,
    AddCodeComponent,
    ViewCodingComponent,
    UpdatecodeComponent,
    McqComponent,
    AnsDetailsComponent,
    FinalComponent,
    UsersComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    SplitterModule,
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule,MatPaginatorModule,MatSortModule,MatTableModule,MatProgressSpinnerModule,MatRadioModule, MatSelectModule, MatCardModule, MatToolbarModule, MatIconModule, MatListModule, MatInputModule, MatFormFieldModule, MatSnackBarModule, MatSlideToggleModule,

  ],
  providers: [authInterceptorProviders,FullScreenService],
  bootstrap: [AppComponent]
})
export class AppModule { }
