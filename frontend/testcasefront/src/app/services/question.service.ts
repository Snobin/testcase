import { HttpClient, HttpEvent, HttpHeaders, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class QuestionService {
  baseUrl = 'http://localhost:8083/question';

  constructor(private http: HttpClient) { }

  public getQuestions(qid) {
    return this.http.get(`${this.baseUrl}/quiz/all/${qid}`);
  }
  public getQuestion(questId) {
    return this.http.get(`${this.baseUrl}/${questId}`);
  }

  public getQuestionsForQuiz(qid) {
    return this.http.get(`${this.baseUrl}/quiz/${qid}`);
  }

  addCodingQuestion(question: any, file: any): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('title', question.title);
    formData.append('desc', question.desc);
    formData.append('ex1input', question.ex1input);
    formData.append('ex1output', question.ex1output);
    formData.append('ex1explanation', question.ex1explanation);
    formData.append('ex2input', question.ex2input);
    formData.append('ex2output', question.ex2output);
    formData.append('ex2explanation', question.ex2explanation);
    formData.append('constraints', question.constraints);
    formData.append('qid', question.qid);
    formData.append('active', (question.active || false).toString());
    formData.append('time',question.time||null);
    
    if (file) {
      formData.append('fileContent', file, file.name);
    }
  
    return this.http.post(`${this.baseUrl}/addCodingQuestion`, formData);
  }

  getCodingQuestion(qid){
    return this.http.post(`${this.baseUrl}/getcode`,qid);
  }
  


  // Add Question
  public addQuestion(question) {
    return this.http.post(`${this.baseUrl}/`, question);
  }
  public updateQuestion(question) {
    return this.http.post(`${this.baseUrl}/update`, question);
  }

  // delete question
  public deleteQuestion(questionId) {
    return this.http.delete(`${this.baseUrl}/${questionId}`);
  }
  public evalQuiz(questions) {    
    return this.http.post(`${this.baseUrl}/eval`, questions)
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
