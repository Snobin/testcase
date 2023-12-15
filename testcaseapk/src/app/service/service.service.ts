import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { McqQuestions } from '../admin/admin-question-section/model/mcq-questions';
@Injectable({
  providedIn: 'root'
})
export class ServiceService {



  private apiUrl = 'http://localhost:8081/execute';
  private mcqcreateUrl= "http://localhost:8082/MCQ/questions";




  constructor(private http: HttpClient) { }

  compileAndTest (codeRequest: any): Observable<any> {
    console.log("hdwiouh")

    return this.http.post<any>(this.apiUrl , codeRequest);
  }

  addquestion(mcq:McqQuestions):Observable<object>{
    return this.http.post(`${this.mcqcreateUrl}`,mcq);
  }
 
}
