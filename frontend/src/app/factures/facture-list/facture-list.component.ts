import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FactureResponse, FactureStatut } from '../../core/models/facture.model';
import { FactureService } from '../../core/services/facture.service';

@Component({
  selector: 'app-facture-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './facture-list.component.html',
  styleUrls: ['./facture-list.component.css']
})
export class FactureListComponent implements OnInit {
  factures: FactureResponse[] = [];
  isLoading = false;
  error: string | null = null;

  constructor(private factureService: FactureService) {}

  ngOnInit(): void {
    this.loadFactures();
  }

  loadFactures(): void {
    this.isLoading = true;
    this.factureService.getFactures().subscribe({
      next: (data) => {
        this.factures = data;
        this.isLoading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des factures. Veuillez réessayer.';
        this.isLoading = false;
        console.error('Error loading factures:', err);
      }
    });
  }

  getStatutClass(statut: FactureStatut): string {
    switch (statut) {
      case FactureStatut.PAID: return 'badge bg-success';
      case FactureStatut.PENDING: return 'badge bg-warning text-dark';
      case FactureStatut.OVERDUE: return 'badge bg-danger';
      case FactureStatut.CANCELLED: return 'badge bg-secondary';
      default: return 'badge bg-secondary';
    }
  }

  deleteFacture(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer cette facture ?')) {
      this.factureService.deleteFacture(id).subscribe({
        next: () => {
          this.loadFactures();
        },
        error: (err) => {
          console.error('Error deleting facture:', err);
          alert('Erreur lors de la suppression de la facture');
        }
      });
    }
  }
}


