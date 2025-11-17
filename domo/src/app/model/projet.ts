import { Departement } from "./departement";
import { Employe } from "./employe";

export interface Projet {
    id: number;
    nom: string;
    dateDebut: Date;
    dateFin: Date;
    departement: Departement;   
    employes: Employe[];
}
