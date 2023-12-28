import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Question } from '../model/question';

@Injectable({
  providedIn: 'root'
})
export class QuestionService {

  
  private baseUrl= "http://localhost:8081/MCQ";

  constructor(private http: HttpClient) { }

  getQuestions(): Observable<Question[]>{
    return this.http.get<Question[]>(`${this.baseUrl}/question-list`);
  }

}
