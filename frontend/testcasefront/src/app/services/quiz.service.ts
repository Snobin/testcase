import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class QuizService {

  constructor(private http:HttpClient) { }
  baseUrl = 'http://localhost:8083/quiz';

  public quizzes(){
    return this.http.get(`${this.baseUrl}/`)
  }
  public addQuiz(quiz){
    return this.http.post(`${this.baseUrl}/`,quiz);
  }
  public deleteQuiz(qId){
    return this.http.delete(`${this.baseUrl}/${qId}`);
  }
  public getQuiz(qId){
    return this.http.get(`${this.baseUrl}/${qId}`);
  }
  public updateQuiz(quiz){
    return this.http.put(`${this.baseUrl}/`,quiz);
  }
  public getQuizCategory(cid){
    return this.http.get(`${this.baseUrl}/category/${cid}`);
  }
  public getActiveQuizzes(){
    return this.http.get(`${this.baseUrl}/active`);
  }
  public getActiveQuizCategory(cid){
     return this.http.get(`${this.baseUrl}/category/active/${cid}`);

  }
}
