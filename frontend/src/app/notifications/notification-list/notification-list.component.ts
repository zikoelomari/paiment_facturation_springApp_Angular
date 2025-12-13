import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotificationResponse, NotificationStatus, NotificationType, NotificationChannel } from '../../core/models/notification.model';
import { NotificationService } from '../../core/services/notification.service';

@Component({
  selector: 'app-notification-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './notification-list.component.html'
})
export class NotificationListComponent implements OnInit {
  notifications: NotificationResponse[] = [];
  isLoading = false;
  error: string | null = null;

  constructor(private notificationService: NotificationService) {}

  ngOnInit(): void {
    this.loadNotifications();
  }

  loadNotifications(): void {
    this.isLoading = true;
    this.notificationService.getNotifications().subscribe({
      next: (data) => {
        this.notifications = data;
        this.isLoading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des notifications. Veuillez réessayer.';
        this.isLoading = false;
        console.error('Error loading notifications:', err);
      }
    });
  }

  getStatusClass(status: NotificationStatus): string {
    switch (status) {
      case NotificationStatus.SENT:
      case NotificationStatus.DELIVERED:
        return 'badge bg-success';
      case NotificationStatus.RETRY:
        return 'badge bg-warning text-dark';
      case NotificationStatus.PENDING:
        return 'badge bg-warning text-dark';
      case NotificationStatus.FAILED:
        return 'badge bg-danger';
      default:
        return 'badge bg-secondary';
    }
  }

  getTypeClass(type: NotificationType): string {
    switch (type) {
      case NotificationType.PAYMENT_CONFIRMATION:
        return 'badge bg-success';
      case NotificationType.INVOICE_OVERDUE:
        return 'badge bg-danger';
      case NotificationType.INVOICE_CREATED:
        return 'badge bg-info';
      case NotificationType.EVENT_REGISTRATION:
        return 'badge bg-primary';
      case NotificationType.EVENT_REMINDER:
        return 'badge bg-secondary';
      default:
        return 'badge bg-secondary';
    }
  }

  getChannelIcon(channel: NotificationChannel): string {
    switch (channel) {
      case NotificationChannel.EMAIL:
        return 'bi-envelope';
      case NotificationChannel.SMS:
        return 'bi-chat';
      case NotificationChannel.PUSH:
        return 'bi-bell';
      case NotificationChannel.IN_APP:
        return 'bi-window';
      default:
        return 'bi-info-circle';
    }
  }
}

