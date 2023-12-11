import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class ServiceService {


  private apiUrl = 'http://localhost:8081/execute/python';

  constructor(private http: HttpClient) { }

  compileAndTestCode(codeRequest: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, codeRequest);
  }
}
