import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  baseUrl='http://localhost:8080/auth';

  constructor(private http: HttpClient) { }

  //generate token

  public generateToken(loginData: any) {
    return this.http.post(`${this.baseUrl}/generate-token`,loginData)
  }

}
