import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Response } from '../model/response';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = 'http://localhost:8081/auth';
  private statusSubject = new BehaviorSubject<boolean>(false);

  status$ = this.statusSubject.asObservable();

  constructor(private http: HttpClient) { }

  // Add user
  addUser(user: any): Observable<Response> {
    return this.http.post<Response>(`${this.baseUrl}/signup`, user);
  }

//update

update(user: any): Observable<Response> {
  return this.http.post<Response>(`${this.baseUrl}/update`, user);
}

  // Set status
  setStatus(status: boolean): void {
    console.log(status);
    this.statusSubject.next(status);
  }
}
