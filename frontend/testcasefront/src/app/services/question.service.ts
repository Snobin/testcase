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
}
