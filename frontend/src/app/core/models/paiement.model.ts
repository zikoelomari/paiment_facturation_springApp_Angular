export enum PaiementStatut {
  PENDING = 'PENDING',
  COMPLETED = 'COMPLETED',
  FAILED = 'FAILED',
  CANCELLED = 'CANCELLED'
}

export interface Paiement {
  id: number;
  factureId: number;
  montant: number;
  methodePaiement: string;
  statut: PaiementStatut;
  datePaiement?: string;
  createdAt?: string;
  updatedAt?: string;
}

export interface PaiementRequest {
  factureId: number;
  montant: number;
  methodePaiement: string;
}

export interface PaiementResponse {
  id: number;
  factureId: number;
  montant: number;
  methodePaiement: string;
  statut: PaiementStatut;
  datePaiement?: string;
  createdAt?: string;
  updatedAt?: string;
}

