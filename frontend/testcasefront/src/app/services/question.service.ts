import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class QuestionService {
  baseUrl = 'http://localhost:8083/question';

  constructor(private http :HttpClient) { }

  public getQuestions(qid){
    return this.http.get(`${this.baseUrl}/quiz/all/${qid}`);
  }

  // Add Question
  public addQuestion(question){
    return this.http.post(`${this.baseUrl}/`,question);
  }
}
