import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Departement } from '../model/departement';

@Injectable({
  providedIn: 'root',
})
export class DepartementService {
  private apiUrl1 = 'http://localhost:8080/departements';
  constructor(private http: HttpClient) {}
  
  /** Liste départements */
  getDepartements(): Observable<Departement[]> {
    return this.http.get<Departement[]>(this.apiUrl1);
  }
}
