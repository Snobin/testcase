import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private _http:HttpClient) { }
  baseUrl = 'http://localhost:8083/category';

  public categories(){
    return this._http.get(`${this.baseUrl}/`)
  }
}
