import { Projet } from "./projet";

export interface Departement {
    id: number;
    nom: string;
    projets: Projet[];
}
