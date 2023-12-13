import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class ServiceService {


  private apiUrl = 'http://localhost:8081/execute';



  constructor(private http: HttpClient) { }

  javaCompile(codeRequest: any): Observable<any> {
    console.log("hdwiouh")

    return this.http.post<any>(this.apiUrl , codeRequest);
  }
  pythonCompile(codeRequest: any): Observable<any> {
    console.log("hdwiouh")

    return this.http.post<any>(this.apiUrl , codeRequest);
  }
}
