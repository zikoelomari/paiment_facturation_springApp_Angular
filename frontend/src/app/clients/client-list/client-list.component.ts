import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ClientResponse, ClientStatus } from '../../core/models/client.model';
import { ClientService } from '../../core/services/client.service';

@Component({
  selector: 'app-client-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './client-list.component.html'
})
export class ClientListComponent implements OnInit {
  clients: ClientResponse[] = [];
  isLoading = false;
  error: string | null = null;

  constructor(private clientService: ClientService) {}

  ngOnInit(): void {
    this.loadClients();
  }

  loadClients(): void {
    this.isLoading = true;
    this.clientService.getClients().subscribe({
      next: (data) => {
        this.clients = data;
        this.isLoading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des clients. Veuillez réessayer.';
        this.isLoading = false;
        console.error('Error loading clients:', err);
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
}


