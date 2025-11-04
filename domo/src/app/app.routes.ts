import { Routes } from '@angular/router';
import { Departements } from './departements/departements';
import { Employes } from './employes/employes';
import { Projets } from './projets/projets';

export const routes: Routes = [
  { path: 'departements', component: Departements },
  { path: 'employes', component: Employes },
  { path: 'projets', component: Projets },

  // route par défaut
  { path: '', redirectTo: '/departements', pathMatch: 'full' },

  // route si l’URL ne correspond à rien
  { path: '**', redirectTo: '/departements' }
];