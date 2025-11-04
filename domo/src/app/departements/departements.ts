import { Component, OnInit } from '@angular/core';
import { DepartementService } from '../service/departement-service';
import { Departement } from '../model/departement';
import { CommonModule } from '@angular/common';
import { NgxPaginationModule } from 'ngx-pagination';

@Component({
  selector: 'app-departements',
  imports: [CommonModule, NgxPaginationModule],
  templateUrl: './departements.html',
  styleUrl: './departements.css',
})
export class Departements {
  departements: Departement[] = [];
  errorMessage = '';
  p: number = 1; // page courante
  
  constructor(private departementService: DepartementService) {}

  ngOnInit(): void {
  this.loadDepartements();
} 
  /* Méthodes en relation avec departement.service */
loadDepartements(): void {
  this.departementService.getDepartements().subscribe({
    next: (data) => {
      this.departements = data;
    },
    error: (err) => {
      this.errorMessage = err.error.message || 'Erreur lors du chargement des départements';
    }
  });
}
}
