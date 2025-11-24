import { Component } from '@angular/core';
import { DepartementService } from '../service/departement-service';
import { Departement } from '../model/departement';
import { CommonModule } from '@angular/common';
import { NgxPaginationModule } from 'ngx-pagination';
import { Projet } from '../model/projet';
import * as bootstrap from 'bootstrap';
import { ToastService } from '../service/toast-service';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-departements',
  imports: [CommonModule, NgxPaginationModule, FormsModule],
  templateUrl: './departements.html',
  styleUrl: './departements.css',
})
export class Departements {

  departements: Departement[] = [];
  errorMessage = '';
  p: number = 1; // page courante
  selectedDepartement: Departement | undefined;
  successMessage = '';
  projets: Projet[] = [];
  nomDep:string='';
  errorMessageModal='';
  selectedId:number |undefined;
  // variable pour stocker l'instance du modal
  private modalInstance: any;
  private modalInstance2: any;
  private modalInstance3: any;
  private modalInstance4: any;
  editDep?: Departement;
  keyword:string='';

  constructor(private departementService: DepartementService, 
              private toastService:ToastService 
  ) {}

  ngOnInit(): void {
  this.loadDepartements();
} 

  /* Méthode component pour lister départements */
loadDepartements(): void {
  this.departementService.getDepartements().subscribe({
    next: (data) => {
      this.departements = data;
    },
    error: (err) => {
      this.errorMessage = err.error || 'Erreur lors du chargement des départements';
    }
  });
}

onSubmit(form: any): void {
  if (form.invalid) return;
  const newDepartement = {
    nom: this.nomDep
  };
  
  this.departementService.createDepartement(newDepartement).subscribe({
    next: (data) => {
      this.loadDepartements();
      form.reset();
      this.modalInstance?.hide();
      this.toastService.show('Ajout effectué avec succès', 'success');
    },
    error: (err) => {
      //this.errorMessageModal = err.error || 'Erreur lors de l’ajout';
      this.toastService.show(err.error, 'danger');
    }
  });
}

// Confirmer la suppression
  confirmDelete() {
    if (!this.selectedId) return;

    this.departementService.deleteDepartement(this.selectedId).subscribe({
      next: (msg: string) => {
        this.loadDepartements(); // Recharge la liste
        this.modalInstance2?.hide();
        this.toastService.show('Suppression effectuée avec succès', 'success');
      },
      error: (err) => {
      //this.errorMessageModal = err.error || 'Erreur lors de la suppression';
      this.toastService.show(err.error, 'danger');
    }
    });
    
  }

  // Confirmer la mise à jour
  confirmEdit() {
    if (!this.editDep) return;

    this.departementService.updateDepartement(this.editDep.id,this.editDep).subscribe({
      next: (data) => {
        this.loadDepartements(); // Recharge la liste
        this.modalInstance3?.hide();
        this.toastService.show('Mise à jour effectuée avec succès', 'success');
      },
      error: (err) => {
      //this.errorMessageModal = err.error || 'Erreur lors de la suppression';
      this.toastService.show(err.error, 'danger');
    }
    });  
  }


// ouverture du modal
  openAddModal() {
    // Vider les champs et erreurs
    this.nomDep = '';
    this.errorMessageModal = '';
    //Création d'une instance modal
    const modalElement = document.getElementById('addDepartementModal');
    if (modalElement) {
      this.modalInstance = new bootstrap.Modal(modalElement, {
        backdrop: 'static',
        keyboard: false
      });
    }
    this.modalInstance.show();
    }
  
  openDeleteModal(id:number) {
    // Vider les champs et erreurs
      this.selectedId = id;
      this.errorMessageModal = '';
    //Création d'une instance modal
      const modalElement2 = document.getElementById('deleteDepartementModal');
      if (modalElement2) {
        this.modalInstance2 = new bootstrap.Modal(modalElement2, {
          backdrop: 'static',
          keyboard: false
        });
      }
      this.modalInstance2.show();
  }

  openEditModal(dept:any) {
    // Vider les champs et erreurs
    this.editDep = { ...dept }; //Clonage pour que toute modification dans le modal ne modifie pas directement le même objet
    this.errorMessageModal = '';
    //Création d'une instance modal
    const modalElement3 = document.getElementById('editDepartementModal');
    if (modalElement3) {
      this.modalInstance3 = new bootstrap.Modal(modalElement3, {
        backdrop: 'static',
        keyboard: false
      });
    }
    this.modalInstance3.show();
    }

  openListingProjetsModal(dept:any) {
    // Vider les champs et erreurs
    this.selectedDepartement = dept;
    this.errorMessageModal = '';
    this.projets =[];
    //Apport des projets
    this.departementService.getProjetsByDepartement(dept.id).subscribe({
      next: (data) => {
        this.projets = data; // Recharge la liste des projets
        //Création d'une instance modal
        const modalElement4 = document.getElementById('listingProjetsByDepartementModal');
        if (modalElement4) {
          this.modalInstance4 = new bootstrap.Modal(modalElement4, {
              backdrop: 'static',
              keyboard: false
        });
        }
        this.modalInstance4?.show();
      },
      error: (err) => {
      //this.errorMessageModal = err.error || 'Erreur lors de la suppression';
      this.toastService.show(err.error, 'danger');
    }
    });    
    }

  filterDepartements() {
    this.departementService.searchDepartements(this.keyword).subscribe({
      next: (data) => {
        this.departements = data;
        //this.loadDepartements(); // Recharge la liste
      },
      error: (err) => {
      //this.errorMessageModal = err.error || 'Erreur lors de la suppression';
      this.toastService.show(err.error, 'danger');
    }
    });  
  }
}
