import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  public loginStatusSubject = new Subject<boolean>();

  baseUrl = 'http://localhost:8081/auth';

  constructor(private http: HttpClient) { }

  public getCurrentUser() {
    return this.http.get(`${this.baseUrl}/current-user`);
  }

  getData() {
    return this.http.get(`${this.baseUrl}/userlist`);
  }

  //generate token
  public generateToken(loginData: any) {
    return this.http.post(`${this.baseUrl}/login`, loginData);
  }

  //login user: set token in localStorage
  public loginUser(token) {
    localStorage.setItem('token', token);
    return true;
  }

  //isLogin: user is logged or not
  public isloggedin() {
    let tokenStr = localStorage.getItem("token");
    if (tokenStr == undefined || tokenStr == '' || tokenStr == null) {
      return false;
    } else {
      return true;
    }
  }

  //logout: remove token from localStorage
  public logout() {
    this.removeStorage();
    return true;
  }

  public removeStorage(){
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    if (localStorage.getItem("quizzes")) {
      let quizzes = JSON.parse(localStorage.getItem("quizzes"));
      for (let index = 0; index < quizzes.length; index++) {
        if (localStorage.getItem(`questions${quizzes[index].qid}`)) {
          localStorage.removeItem(`questions${quizzes[index].qid}`);
        }
      }
      localStorage.removeItem("quizzes");
    }
    if (localStorage.getItem("codingQuestions")) {
      localStorage.removeItem("codingQuestions");
    }
  }

  //get token
  public getToken() {
    return localStorage.getItem("token");
  }

  //set userDetails
  public setUser(user) {
    localStorage.setItem("user", JSON.stringify(user));
  }

  //get userDetails
  public getUser() {
    let userStr = localStorage.getItem("user");
    if (userStr != null) {
      return JSON.parse(userStr);
    } else {
      this.logout();
      return null;
    }
  }

  // get user role
  public getUserRole() {
    let user = this.getUser();
    return user.authorities[0].authority;
  }


}
