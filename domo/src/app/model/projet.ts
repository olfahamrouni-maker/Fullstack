import { Departement } from "./departement";
import { Employe } from "./employe";

export interface Projet {
    id: number;
    titre: string;
    description: string;
    dateDebut: Date;
    dateFin: Date;
    departement: Departement;   
    employes: Employe[];
}
