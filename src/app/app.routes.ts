import { Routes } from '@angular/router';
import { Departements } from './departements/departements';
import { Employes } from './employes/employes';
import { Projets } from './projets/projets';
import { Login } from './login/login';
import { Home } from './home/home';
import { RoleGuard } from './service/role-guard';
import { AuthGuard } from './service/auth-guard';

export const routes: Routes = [
  // route par défaut
  { path: 'home', component: Home, canActivate: [AuthGuard] },
  { path: 'login', component: Login },

  { path: 'departements', component: Departements, 
    canActivate: [RoleGuard], data: { role: 'ROLE_ADMIN' }},
  { path: 'employes', component: Employes, 
    canActivate: [RoleGuard],    data: { role: 'ROLE_ADMIN' } },
  { path: 'projets', component: Projets,
    canActivate: [RoleGuard],   data: { role: 'ROLE_RESPONSABLE_PROJET' } 
   },

  // route si l’URL ne correspond à rien
  { path: '', redirectTo: 'login', pathMatch:'full'}
];