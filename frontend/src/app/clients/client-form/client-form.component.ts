import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ClientRequest, ClientUpdate, ClientStatus } from '../../core/models/client.model';
import { ClientService } from '../../core/services/client.service';

@Component({
  selector: 'app-client-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './client-form.component.html'
})
export class ClientFormComponent implements OnInit {
  clientForm: FormGroup;
  isEditMode = false;
  clientId: number | null = null;
  isLoading = false;
  error: string | null = null;
  statuses = Object.values(ClientStatus);

  constructor(
    private fb: FormBuilder,
    private clientService: ClientService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.clientForm = this.fb.group({
      firstName: ['', [Validators.required, Validators.minLength(2)]],
      lastName: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      status: [ClientStatus.ACTIVE, Validators.required],
      password: ['', [Validators.minLength(6)]]
    });
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.isEditMode = true;
        this.clientId = +id;
        this.loadClient(this.clientId);
      }
    });
  }

  loadClient(id: number): void {
    this.isLoading = true;
    this.clientService.getClient(id).subscribe({
      next: (client) => {
        this.clientForm.patchValue({
          firstName: client.firstName,
          lastName: client.lastName,
          email: client.email,
          status: client.status
        });
        this.clientForm.get('password')?.clearValidators();
        this.clientForm.get('password')?.updateValueAndValidity();
        this.isLoading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des données du client';
        this.isLoading = false;
        console.error('Error loading client:', err);
      }
    });
  }

  onSubmit(): void {
    if (this.clientForm.invalid) {
      return;
    }

    this.isLoading = true;
    const formData = { ...this.clientForm.value };

    // Remove password if it's empty (for updates)
    if (!formData.password) {
      delete formData.password;
    }

    const request = this.isEditMode && this.clientId
      ? this.clientService.updateClient(this.clientId, formData as ClientUpdate)
      : this.clientService.createClient(formData as ClientRequest);

    request.subscribe({
      next: () => {
        this.router.navigate(['/clients']);
      },
      error: (err) => {
        this.error = this.isEditMode
          ? 'Erreur lors de la mise à jour du client. Veuillez réessayer.'
          : 'Erreur lors de la création du client. Veuillez réessayer.';
        this.isLoading = false;
        console.error('Error saving client:', err);
      }
    });
  }

  onCancel(): void {
    this.router.navigate(['/clients']);
  }

  get f() {
    return this.clientForm.controls;
  }
}


