import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/auth';

  constructor(private http: HttpClient, private router:Router ) {}
 

  login(credentials: any): Observable<any> {
      return this.http.post<any>(this.baseUrl+"/login", credentials)
        .pipe(
          tap(response => {
            localStorage.setItem('token', response.token);
          })
        );
}
  
  getToken() {
    return localStorage.getItem('token');
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

  isLoggedIn() {
    return this.getToken() != null;
  }

  getRole(): string | null {
  const token = this.getToken();
  if (!token) return null;

  const payload = JSON.parse(atob(token.split('.')[1]));
  return payload.role;   // le claim côté backend
}

getUsername(): string | null {
  const token = localStorage.getItem('token');
  if (!token) {
    return null;
  }

  const payload = JSON.parse(atob(token.split('.')[1]));
  return payload.sub;     // le username est dans "sub"

}

}
