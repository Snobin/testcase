import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  baseUrl = 'http://localhost:8081/auth';

  constructor(private http: HttpClient) { }

  //generate token
  public generateToken(loginData: any) {
    return this.http.post(`${this.baseUrl}/login`, loginData);
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
    localStorage.removeItem("role");
    return true;
  }

  //get token
  public getToken() {
    return localStorage.getItem("token");
  }

  //set userDetails
  public setUser(user) {
    localStorage.setItem("user", user);
  }

  //get userDetails
  public getUser() {
    let userStr = localStorage.getItem("user");
    if (userStr != null) {
      return userStr;
    } else {
      this.logout();
      return null;
    }
  }

  //get user role
  public getUserRole() {
    return localStorage.getItem("role");
  }

  //set user role
  public setUserRole(role) {
    localStorage.setItem("role", role);
  }

}
