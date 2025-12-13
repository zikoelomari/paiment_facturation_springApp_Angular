import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ClientResponse, ClientStatus } from '../../core/models/client.model';
import { ClientService } from '../../core/services/client.service';

@Component({
  selector: 'app-client-details',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './client-details.component.html'
})
export class ClientDetailsComponent implements OnInit {
  client: ClientResponse | null = null;
  isLoading = false;
  error: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private clientService: ClientService
  ) {}

  ngOnInit(): void {
    this.loadClient();
  }

  loadClient(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) {
      this.error = 'ID client invalide';
      return;
    }

    this.isLoading = true;
    this.clientService.getClient(+id).subscribe({
      next: (client) => {
        this.client = client;
        this.isLoading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des détails du client';
        this.isLoading = false;
        console.error('Error loading client:', err);
      }
    });
  }

  getStatusClass(status: ClientStatus): string {
    switch (status) {
      case ClientStatus.ACTIVE: return 'badge bg-success';
      case ClientStatus.INACTIVE: return 'badge bg-secondary';
      case ClientStatus.SUSPENDED: return 'badge bg-warning text-dark';
      default: return 'badge bg-secondary';
    }
  }

  onEdit(): void {
    if (this.client) {
      this.router.navigate(['/clients', this.client.id, 'edit']);
    }
  }

  onBack(): void {
    this.router.navigate(['/clients']);
  }
}


