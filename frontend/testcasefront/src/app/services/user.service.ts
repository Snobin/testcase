import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Response } from '../model/response';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  baseUrl='http://localhost:8081/auth';
  showTime: boolean;
  countdownMinutes: number = 10;
  countdownSeconds: number = 0;

  constructor(private http:HttpClient) { }

  //add user
  public addUser(user:any):Observable<Response>{
    return this.http.post<Response>(`${this.baseUrl}/signup`,user);
  }

  public timeShow(){
    return this.showTime;
  }

  startCountdown(): void {
    const countdownInterval = setInterval(() => {
      if (this.countdownSeconds === 0) {
        if (this.countdownMinutes === 0) {
          clearInterval(countdownInterval);
          // Handle what happens when the countdown reaches 0
        } else {
          this.countdownMinutes--;
          this.countdownSeconds = 59;
        }
      } else {
        this.countdownSeconds--;
      }
    }, 1000);
  }
  
}
