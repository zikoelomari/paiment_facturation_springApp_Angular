import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { FactureResponse, FactureStatut } from '../../core/models/facture.model';
import { FactureService } from '../../core/services/facture.service';

@Component({
  selector: 'app-facture-details',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './facture-details.component.html'
})
export class FactureDetailsComponent implements OnInit {
  facture: FactureResponse | null = null;
  isLoading = false;
  error: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private factureService: FactureService
  ) {}

  ngOnInit(): void {
    this.loadFacture();
  }

  loadFacture(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) {
      this.error = 'ID facture invalide';
      return;
    }

    this.isLoading = true;
    this.factureService.getFacture(+id).subscribe({
      next: (facture) => {
        this.facture = facture;
        this.isLoading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des détails de la facture';
        this.isLoading = false;
        console.error('Error loading facture:', err);
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

  markAsPaid(): void {
    if (this.facture) {
      this.factureService.markAsPaid(this.facture.id).subscribe({
        next: () => {
          this.loadFacture();
        },
        error: (err) => {
          console.error('Error marking facture as paid:', err);
          alert('Erreur lors de la mise à jour');
        }
      });
    }
  }

  onEdit(): void {
    if (this.facture) {
      this.router.navigate(['/factures', this.facture.id, 'edit']);
    }
  }

  onBack(): void {
    this.router.navigate(['/factures']);
  }
}


