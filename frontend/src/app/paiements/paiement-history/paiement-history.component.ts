import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { PaiementResponse, PaiementStatut } from '../../core/models/paiement.model';
import { PaiementService } from '../../core/services/paiement.service';

@Component({
  selector: 'app-paiement-history',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './paiement-history.component.html'
})
export class PaiementHistoryComponent implements OnInit {
  paiements: PaiementResponse[] = [];
  isLoading = false;
  error: string | null = null;

  constructor(private paiementService: PaiementService) {}

  ngOnInit(): void {
    this.loadPaiements();
  }

  loadPaiements(): void {
    this.isLoading = true;
    this.paiementService.getPaiements().subscribe({
      next: (data) => {
        this.paiements = data;
        this.isLoading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des paiements. Veuillez réessayer.';
        this.isLoading = false;
        console.error('Error loading paiements:', err);
      }
    });
  }

  getStatutClass(statut: PaiementStatut): string {
    switch (statut) {
      case PaiementStatut.COMPLETED: return 'badge bg-success';
      case PaiementStatut.PENDING: return 'badge bg-warning text-dark';
      case PaiementStatut.FAILED: return 'badge bg-danger';
      case PaiementStatut.CANCELLED: return 'badge bg-info';
      default: return 'badge bg-secondary';
    }
  }
}

