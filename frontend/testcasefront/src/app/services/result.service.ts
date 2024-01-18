import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ResultService {

  constructor(private http:HttpClient) { }

   apiUrl="http://localhost:8083/question";
  

   getData(){
    return this.http.get(`${this.apiUrl}/singleResult`);
   }
   getDataByUser(user){
    return this.http.post(`${this.apiUrl}/getByUser`,user);
   }
}
