import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PaiementRequest } from '../../core/models/paiement.model';
import { PaiementService } from '../../core/services/paiement.service';
import { FactureService } from '../../core/services/facture.service';
import { FactureResponse, FactureStatut } from '../../core/models/facture.model';

@Component({
  selector: 'app-paiement-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './paiement-form.component.html'
})
export class PaiementFormComponent implements OnInit {
  paiementForm: FormGroup;
  isLoading = false;
  error: string | null = null;
  factures: FactureResponse[] = [];
  selectedFacture: FactureResponse | null = null;

  constructor(
    private fb: FormBuilder,
    private paiementService: PaiementService,
    private factureService: FactureService,
    private router: Router
  ) {
    this.paiementForm = this.fb.group({
      factureId: ['', [Validators.required]],
      montant: ['', [Validators.required, Validators.min(0.01)]],
      methodePaiement: ['CARD', [Validators.required]]
    });
  }

  ngOnInit(): void {
    this.loadFactures();
    this.paiementForm.get('factureId')?.valueChanges.subscribe(factureId => {
      if (factureId) {
        this.loadFactureDetails(+factureId);
      }
    });
  }

  loadFactures(): void {
    this.factureService.getFactures().subscribe({
      next: (factures) => {
        // Filtrer seulement les factures en attente
        this.factures = factures.filter(f => f.statut === FactureStatut.PENDING);
      },
      error: (err) => {
        console.error('Error loading factures:', err);
      }
    });
  }

  loadFactureDetails(factureId: number): void {
    this.factureService.getFacture(factureId).subscribe({
      next: (facture) => {
        this.selectedFacture = facture;
        this.paiementForm.patchValue({
          montant: facture.montant
        });
      },
      error: (err) => {
        console.error('Error loading facture details:', err);
      }
    });
  }

  onSubmit(): void {
    if (this.paiementForm.invalid) {
      return;
    }

    this.isLoading = true;
    const paiementData: PaiementRequest = {
      ...this.paiementForm.value,
      factureId: +this.paiementForm.value.factureId,
      montant: +this.paiementForm.value.montant
    };

    this.paiementService.processPaiement(paiementData).subscribe({
      next: () => {
        this.router.navigate(['/paiements']);
      },
      error: (err) => {
        this.error = 'Erreur lors du traitement du paiement. Veuillez réessayer.';
        this.isLoading = false;
        console.error('Error processing paiement:', err);
      }
    });
  }

  onCancel(): void {
    this.router.navigate(['/paiements']);
  }

  get f() {
    return this.paiementForm.controls;
  }
}


