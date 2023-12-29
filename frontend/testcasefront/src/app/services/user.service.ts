import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Response } from '../model/response';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  baseUrl='http://localhost:8081/auth';

  constructor(private http:HttpClient) { }

  //add user
  public addUser(user:any):Observable<Response>{
    return this.http.post<Response>(`${this.baseUrl}/signup`,user);
  }
  
}