import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { AddQuestion } from '../model/AddQuestion';
import { takeUntil } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CodeService {

  private apiUrl = 'http://localhost:8082/execute';
  private baseUrl = 'http://localhost:8083/question';
  private url = 'http://localhost:8083/code';

  constructor(private http: HttpClient) { }

  compileAndTest(codeRequest: any, cancelSubject: Subject<void>): Observable<any> {
    return this.http.post<any>(this.apiUrl, codeRequest)
    .pipe(
      takeUntil(cancelSubject),
    );
  }

  questionReq(qid) {
    return this.http.get(`${this.baseUrl}/qndata/${qid}`);
  }

  public Questions() {
    return this.http.get(`${this.baseUrl}/`);
  }

  public codingQuestions() {
    return this.http.get(`${this.url}`);
  }
  public activeCodingQuestions() {
    return this.http.get(`${this.url}/active`);
  }

  public updateCode(question: AddQuestion, file: any) {
    console.log(question.desc);
    
    const formData: FormData = new FormData();
    formData.append('title', question.title );
    formData.append('desc', question.desc) ;
    formData.append('ex1input', question.ex1input );
    formData.append('ex1output', question.ex1output);
    formData.append('ex1explanation', question.ex1explanation);
    formData.append('ex2input', question.ex2input );
    formData.append('ex2output', question.ex2output );
    formData.append('ex2explanation', question.ex2explanation );
    formData.append('constraints', question.constraints);
    formData.append('qid', question.qid );
    formData.append('active', (question.active || false).toString());
  
    console.log(50, file);
  
    if (file) {
      formData.append('fileContent', file, file.name);
    }
  
    return this.http.put(`${this.baseUrl}/updateCodingQuestion`, formData);
  }
  


}
