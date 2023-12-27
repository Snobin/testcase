import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  baseUrl = 'http://localhost:8080/auth';

  constructor(private http: HttpClient) { }

  //generate token
  public generateToken(loginData: any) {
    return this.http.post(`${this.baseUrl}/generate-token`, loginData);
  }

  //login user: set token in localStorage
  loginUser(token) {
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
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    return true;
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
      return JSON.stringify(userStr);
    } else {
      this.logout();
      return null;
    }
  }

  //get user role
  public getUserRole() {
    let user = this.getUser();
    // return user.authorities[0].authorities
  }

}
