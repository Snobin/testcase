import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ResultService {

  constructor(private http:HttpClient) { }

   apiUrl="http://localhost:8083/question/singleResult";

   getData(){
    return this.http.get(this.apiUrl);
   }
}
