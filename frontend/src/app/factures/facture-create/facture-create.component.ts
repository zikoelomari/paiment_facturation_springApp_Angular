import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { FactureRequest, FactureStatut } from '../../core/models/facture.model';
import { FactureService } from '../../core/services/facture.service';
import { ClientService } from '../../core/services/client.service';
import { ClientResponse } from '../../core/models/client.model';

@Component({
  selector: 'app-facture-create',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './facture-create.component.html'
})
export class FactureCreateComponent implements OnInit {
  factureForm: FormGroup;
  isLoading = false;
  error: string | null = null;
  clients: ClientResponse[] = [];

  constructor(
    private fb: FormBuilder,
    private factureService: FactureService,
    private clientService: ClientService,
    private router: Router
  ) {
    this.factureForm = this.fb.group({
      clientId: ['', [Validators.required]],
      montant: ['', [Validators.required, Validators.min(0.01)]],
      dateEmission: ['', [Validators.required]],
      dateEcheance: [''],
      description: ['']
    });
  }

  ngOnInit(): void {
    this.loadClients();
  }

  loadClients(): void {
    this.clientService.getClients().subscribe({
      next: (clients) => {
        this.clients = clients;
      },
      error: (err) => {
        console.error('Error loading clients:', err);
      }
    });
  }

  onSubmit(): void {
    if (this.factureForm.invalid) {
      return;
    }

    this.isLoading = true;
    const factureData: FactureRequest = {
      ...this.factureForm.value,
      clientId: +this.factureForm.value.clientId,
      montant: +this.factureForm.value.montant
    };

    this.factureService.createFacture(factureData).subscribe({
      next: () => {
        this.router.navigate(['/factures']);
      },
      error: (err) => {
        this.error = 'Erreur lors de la création de la facture. Veuillez réessayer.';
        this.isLoading = false;
        console.error('Error creating facture:', err);
      }
    });
  }

  onCancel(): void {
    this.router.navigate(['/factures']);
  }

  get f() {
    return this.factureForm.controls;
  }
}

