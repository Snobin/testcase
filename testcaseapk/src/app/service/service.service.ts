import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class ServiceService {



  private apiUrl = 'http://localhost:8081/execute/python';


  constructor(private http: HttpClient) { }

  javaCompile(codeRequest: any): Observable<any> {
    return this.http.post<any>(this.apiUrl + "java", codeRequest);
  }
  pythonCompile(codeRequest: any): Observable<any> {
    return this.http.post<any>(this.apiUrl + "python", codeRequest);
  }
}
