import { Routes } from '@angular/router';
import { NotificationListComponent } from './notification-list/notification-list.component';
import { authGuard } from '../core/auth.guard';

export const notificationsRoutes: Routes = [
  { path: '', component: NotificationListComponent, canActivate: [authGuard] }
];


