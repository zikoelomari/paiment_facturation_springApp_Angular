export enum FactureStatut {
  PENDING = 'PENDING',
  PAID = 'PAID',
  CANCELLED = 'CANCELLED',
  OVERDUE = 'OVERDUE'
}

export interface Facture {
  id: number;
  numeroFacture: string;
  clientId: number;
  montant: number;
  dateEmission: string;
  dateEcheance?: string;
  statut: FactureStatut;
  description?: string;
  createdAt?: string;
  updatedAt?: string;
}

export interface FactureRequest {
  clientId: number;
  montant: number;
  dateEmission: string;
  dateEcheance?: string;
  description?: string;
}

export interface FactureUpdate {
  montant?: number;
  dateEcheance?: string;
  statut?: FactureStatut;
  description?: string;
}

export interface FactureResponse {
  id: number;
  numeroFacture: string;
  clientId: number;
  montant: number;
  dateEmission: string;
  dateEcheance?: string;
  statut: FactureStatut;
  description?: string;
  createdAt?: string;
  updatedAt?: string;
}


