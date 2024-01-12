import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CodeService {

  private apiUrl = 'http://localhost:8082/execute';
  private baseUrl = 'http://localhost:8083/question';
  private url='http://localhost:8083/code';

  constructor(private http: HttpClient) { }

  compileAndTest(codeRequest: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, codeRequest);
  }

  questionReq(qid) {
    return this.http.get(`${this.baseUrl}/qndata/${qid}`);
  }

  public Questions() {
    return this.http.get(`${this.baseUrl}/`);
  }

  public codingQuestions(){
    return this.http.get(`${this.url}`);
  }

  public updateCode(data){
    return this.http.post(`${this.baseUrl}`,data);
  }


}
