import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CodeService {

  private apiUrl = 'http://localhost:8081/execute';




  constructor(private http: HttpClient) { }

  compileAndTest (codeRequest: any): Observable<any> {
    console.log("hdwiouh")

    return this.http.post<any>(this.apiUrl , codeRequest);
  }
}
