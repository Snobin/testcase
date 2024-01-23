import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Response } from '../model/response';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  baseUrl='http://localhost:8081/auth';
  countdownSeconds: number;
  countdownMinutes: any;

  constructor(private http:HttpClient) { }

  //add user
  public addUser(user:any):Observable<Response>{
    return this.http.post<Response>(`${this.baseUrl}/signup`,user);
  }


  private statusSubject = new BehaviorSubject<boolean>(false);

  status$ = this.statusSubject.asObservable();

  startCountdown(): void {
    const countdownInterval = setInterval(() => {
      this.countdownSeconds--;

      if (this.countdownSeconds < 0) {
        this.countdownMinutes--;

        this.countdownSeconds = 59;
      }

      if (this.countdownMinutes === 0 && this.countdownSeconds === 0) {
        clearInterval(countdownInterval);
        this.statusSubject.next(false);
      }
    }, 1000);
  }

  // resetTimer(): void {
  //   this.countdownMinutes = 1;
  //   this.countdownSeconds = 0;
  // }

  setStatus(status: boolean): void {
    console.log(status);
    
    this.statusSubject.next(status);
  }
  
}
