export enum NotificationType {
  SYSTEM_ALERT = 'SYSTEM_ALERT',
  PAYMENT_CONFIRMATION = 'PAYMENT_CONFIRMATION',
  INVOICE_OVERDUE = 'INVOICE_OVERDUE',
  INVOICE_CREATED = 'INVOICE_CREATED',
  EVENT_REGISTRATION = 'EVENT_REGISTRATION',
  EVENT_REMINDER = 'EVENT_REMINDER'
}

export enum NotificationChannel {
  EMAIL = 'EMAIL',
  SMS = 'SMS',
  PUSH = 'PUSH',
  IN_APP = 'IN_APP'
}

export enum NotificationStatus {
  PENDING = 'PENDING',
  SENT = 'SENT',
  FAILED = 'FAILED',
  DELIVERED = 'DELIVERED',
  RETRY = 'RETRY'
}

export interface Notification {
  id: number;
  recipientId: number;
  recipientEmail: string;
  type: NotificationType;
  channel: NotificationChannel;
  subject: string;
  content: string;
  status: NotificationStatus;
  sentDate?: string;
  deliveryStatus?: string;
  errorMessage?: string;
  retryCount?: number;
  createdAt?: string;
}

export interface NotificationResponse {
  id: number;
  recipientId: number;
  recipientEmail: string;
  type: NotificationType;
  channel: NotificationChannel;
  subject: string;
  content: string;
  status: NotificationStatus;
  sentDate?: string;
  deliveryStatus?: string;
  errorMessage?: string;
  retryCount?: number;
  createdAt?: string;
}

