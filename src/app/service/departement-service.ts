import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Departement } from '../model/departement';
import { Projet } from '../model/projet';

@Injectable({
  providedIn: 'root',
})
export class DepartementService {
  private apiUrl1 = 'http://localhost:8080/departements';
  private apiUrl2 = 'http://localhost:8080/departement-projet';
  constructor(private http: HttpClient) {} //Injection de service
  
  /*  0: Liste départements */
  getDepartements(): Observable<Departement[]> {
    return this.http.get<Departement[]>(this.apiUrl1);
  }
  /*   1 : CREATION DEPARTEMENT  */
  createDepartement(departement: { nom: string }): Observable<Departement> {
    return this.http.post<Departement>(`${this.apiUrl1}`, departement);
  }
  /*   3 : RECHERCHE DEPARTEMENT PAR ID  */
  getDepartementById(id: number): Observable<Departement> {
    return this.http.get<Departement>(`${this.apiUrl1}/${id}`);
  }
  /*   4 : SUPPRESSION DEPARTEMENT  */
  deleteDepartement(id: number): Observable<any> {
  return this.http.delete(`${this.apiUrl1}/${id}`, { responseType: 'text' });
}
  /*   5 : MISE À JOUR DEPARTEMENT  */
  updateDepartement(id: number, departement: Departement): Observable<Departement> {
    return this.http.put<Departement>(`${this.apiUrl1}/${id}`, departement);
  }
  /*   6 : RECHERCHE PAR MOT CLÉ  */
  searchDepartements(keyword: string): Observable<Departement[]> {
    return this.http.get<Departement[]>(`${this.apiUrl1}/search?keyword=${keyword}`);
  }
  /*   7 : AFFECTER PROJET À DÉPARTEMENT  */
affecterProjetAuDepartement(projetId: number, departementId: number): Observable<Projet> {
  return this.http.post<Projet>(`${this.apiUrl2}/${departementId}/projets/${projetId}`, {});
}
/*   8 : RETIRER PROJET D’UN DÉPARTEMENT  */
retirerProjetDuDepartement(projetId: number): Observable<Projet> {
  return this.http.delete<Projet>(`${this.apiUrl2}/projets/${projetId}`);
}
/*   9 : LISTER PROJETS D’UN DÉPARTEMENT  */
getProjetsByDepartement(departementId: number): Observable<Projet[]> {
  return this.http.get<Projet[]>(`${this.apiUrl2}/departement/${departementId}/projets`);
}
/*   10 : OBTENIR DÉPARTEMENT D’UN PROJET  */
getDepartementByProjet(projetId: number): Observable<Departement> {
  return this.http.get<Departement>(`${this.apiUrl2}/projets/${projetId}/departement`);
}
}

