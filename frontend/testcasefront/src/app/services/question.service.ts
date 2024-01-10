import { HttpClient, HttpEvent, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({  
  providedIn: 'root'
})
export class QuestionService {
  baseUrl = 'http://localhost:8083/question';

  constructor(private http :HttpClient) { }

  public getQuestions(qid){
    return this.http.get(`${this.baseUrl}/quiz/all/${qid}`);
  }

  public getQuestionsForQuiz(qid){
    return this.http.get(`${this.baseUrl}/quiz/${qid}`);
  }

  // Add Coding Question
  addCodingQuestion(question: any): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('heading', question.heading);
    formData.append('description', question.description);
    formData.append('example1', question.example1);
    formData.append('example2', question.example2);
    formData.append('constraints', question.constraints);
    if (question.file) {
      formData.append('file', question.file, question.file.name);
    }
    return this.http.post(`${this.baseUrl}/addCodingQuestion`, formData)
  }

  // Add Question
  public addQuestion(question){
    return this.http.post(`${this.baseUrl}/`,question);
  }

  // delete question
  public deleteQuestion(questionId){
    return this.http.delete(`${this.baseUrl}/${questionId}`);
  }
  public evalQuiz(questions){
    return this.http.post(`${this.baseUrl}/eval`,questions)
  }
  upload(file: File): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();

    formData.append('file', file);

    const req = new HttpRequest('POST', `${this.baseUrl}/upload`, formData, {
      reportProgress: true,
      responseType: 'json'
    });

    return this.http.request(req);
  }

}
