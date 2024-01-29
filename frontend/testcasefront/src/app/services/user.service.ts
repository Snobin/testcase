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

  constructor(private http: HttpClient) {
    this.countdownMinutesSubject = new BehaviorSubject<number>(10);
    this.countdownSecondsSubject = new BehaviorSubject<number>(0);

    this.countdownMinutes$ = this.countdownMinutesSubject.asObservable();
    this.countdownSeconds$ = this.countdownSecondsSubject.asObservable();
   }

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

  uploadExcelFile(file: File): Observable<any> {
    const formData = new FormData();
    formData.append('excelFile', file);
    return this.http.post<any>(`${this.baseUrl}/upload`, formData);
  }


  private countdownMinutesSubject: BehaviorSubject<number>;
  private countdownSecondsSubject: BehaviorSubject<number>;

  public countdownMinutes$: Observable<number>;
  public countdownSeconds$: Observable<number>;

 

  get countdownMinutes(): number {
    return this.countdownMinutesSubject.value;
  }

  set countdownMinutes(value: number) {
    this.countdownMinutesSubject.next(value);
  }

  get countdownSeconds(): number {
    return this.countdownSecondsSubject.value;
  }

  set countdownSeconds(value: number) {
    this.countdownSecondsSubject.next(value);
  }


}
