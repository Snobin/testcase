import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SignupComponent } from './pages/signup/signup.component';
import { DashboardComponent } from './pages/admin/dashboard/dashboard.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { LoginComponent } from './pages/login/login.component';

import { UserDashboardComponent } from './pages/user/user-dashboard/user-dashboard.component';
import { AdminGuard } from './services/admin.guard';
import { UserGuard } from './services/user.guard';
import { ViewCategoriesComponent } from './pages/admin/view-categories/view-categories.component';

import { HomeComponent } from './pages/home/home.component';
import { AddCategoriesComponent } from './pages/admin/add-categories/add-categories.component';
import { ViewQuizzesComponent } from './pages/admin/view-quizzes/view-quizzes.component';
import { AddQuizComponent } from './pages/admin/add-quiz/add-quiz.component';
import { UpdateQuizComponent } from './pages/admin/update-quiz/update-quiz.component';
import { ViewQuizQuestionsComponent } from './pages/admin/view-quiz-questions/view-quiz-questions.component';
import { AddQuestionComponent } from './pages/admin/add-question/add-question.component';
import { LoadQuizComponent } from './pages/user/load-quiz/load-quiz.component';
import { InstructionsComponent } from './pages/user/instructions/instructions.component';
import { StartComponent } from './pages/user/start/start.component';
import { AddCodingComponent } from './pages/admin/add-coding/add-coding.component';
import { ViewAnswerComponent } from './pages/admin/view-answer/view-answer.component';
import { ConsoleComponent } from './pages/user/console/console.component';
import { ViewCodingComponent } from './pages/admin/view-coding/view-coding.component';
import { AddCodeComponent } from './pages/admin/add-code/add-code.component';
import { UpdatecodeComponent } from './pages/admin/updatecode/updatecode.component';
import { AnsDetailsComponent } from './pages/admin/ans-details/ans-details.component';
import { FinalComponent } from './pages/user/final/final.component';
import { UsersComponent } from './pages/admin/users/users.component';
import { AdminupdateComponent } from './pages/admin/adminupdate/adminupdate.component';


const routes: Routes = [
  {
    path: 'signup', component: SignupComponent
  },
  {
    path: '', redirectTo: "signup", pathMatch: 'full'
  },
  {
    path: 'login',
    component: LoginComponent,
    pathMatch: 'full'
  },
  {
    path: 'home',
    component: HomeComponent,
    pathMatch: 'full'
  },
  {
    path: 'instructions',
    component: InstructionsComponent
  },
  {
    path: 'admin',
    component: DashboardComponent,
    canActivate: [AdminGuard],
    children: [
      {
        path: '',
        component: ProfileComponent
      },
      {
        path:'users',
        component:UsersComponent
      },
      {
        path: 'categories',
        component: ViewCategoriesComponent
      }, {
        path: 'add-categories',
        component: AddCategoriesComponent
      }
      , {
        path: 'quizzes',
        component: ViewQuizzesComponent
      }
      , {
        path: 'add-quiz',
        component: AddQuizComponent
      }, {
        path: 'quiz/:qid',
        component: UpdateQuizComponent
      }, {
        path: 'view-questions/:qid/:title',
        component: ViewQuizQuestionsComponent
      }, {
        path: 'add-question/:qid/:title',
        component: AddQuestionComponent
      }, {
        path: 'add-code',
        component: AddCodeComponent
      }, {
        path: 'view-answer',
        component: ViewAnswerComponent
      }, {
        path: 'view-code',
        component: ViewCodingComponent
      }, {
        path: 'code/:qid',
        component: UpdatecodeComponent
      },
      {
        path: 'update',
        component: AdminupdateComponent
      },

       { path: 'details/:userId', component: AnsDetailsComponent },
    ]
  },
  {
    path: 'user-dashboard',
    component: UserDashboardComponent,
    canActivate: [UserGuard],
    children: [
      {
        path: ':title/:catId',
        component: LoadQuizComponent
      }
    ],
  },
  {
    path: 'start/:qid',
    component: StartComponent,
    canActivate: [UserGuard]
  },
  {
    path: 'coding/:qid',
    component: ConsoleComponent
  },{
    path:'final',
    component:FinalComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
