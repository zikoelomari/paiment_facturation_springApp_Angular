import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FactureService } from '../../core/services/facture.service';
import { PaiementService } from '../../core/services/paiement.service';
import { ClientService } from '../../core/services/client.service';
import { NotificationService } from '../../core/services/notification.service';
import { FactureStatut } from '../../core/models/facture.model';
import { NotificationStatus } from '../../core/models/notification.model';

@Component({
  selector: 'app-dashboard-home',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './dashboard-home.component.html',
  styleUrls: ['./dashboard-home.component.css']
})
export class DashboardHomeComponent implements OnInit {
  stats = {
    totalClients: 0,
    totalFactures: 0,
    facturesEnAttente: 0,
    facturesImpayees: 0,
    totalPaiements: 0,
    notificationsNonLues: 0
  };
  loading = true;

  constructor(
    private clientService: ClientService,
    private factureService: FactureService,
    private paiementService: PaiementService,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.loadStats();
  }

  loadStats(): void {
    this.loading = true;

    // Load clients count
    this.clientService.getClients().subscribe({
      next: (clients) => {
        this.stats.totalClients = clients.length;
        this.checkLoadingComplete();
      },
      error: (err) => {
        console.error('Error loading clients:', err);
        this.checkLoadingComplete();
      }
    });

    // Load factures count
    this.factureService.getFactures().subscribe({
      next: (factures) => {
        this.stats.totalFactures = factures.length;
        this.stats.facturesEnAttente = factures.filter(f => f.statut === FactureStatut.PENDING).length;
        this.checkLoadingComplete();
      },
      error: (err) => {
        console.error('Error loading factures:', err);
        this.checkLoadingComplete();
      }
    });

    // Load overdue factures
    this.factureService.getOverdueFactures().subscribe({
      next: (factures) => {
        this.stats.facturesImpayees = factures.length;
        this.checkLoadingComplete();
      },
      error: (err) => {
        console.error('Error loading overdue factures:', err);
        this.checkLoadingComplete();
      }
    });

    // Load paiements count
    this.paiementService.getPaiements().subscribe({
      next: (paiements) => {
        this.stats.totalPaiements = paiements.length;
        this.checkLoadingComplete();
      },
      error: (err) => {
        console.error('Error loading paiements:', err);
        this.checkLoadingComplete();
      }
    });

    // Load notifications count
    this.notificationService.getNotifications().subscribe({
      next: (notifications) => {
        this.stats.notificationsNonLues = notifications.filter(n => n.status === NotificationStatus.PENDING).length;
        this.checkLoadingComplete();
      },
      error: (err) => {
        console.error('Error loading notifications:', err);
        this.checkLoadingComplete();
      }
    });
  }

  private checkLoadingComplete(): void {
    // Simple check - in production, use a more robust approach
    this.loading = false;
  }
}

