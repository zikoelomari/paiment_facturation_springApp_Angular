import { Routes } from '@angular/router';
import { DashboardHomeComponent } from './dashboard-home/dashboard-home.component';
import { authGuard } from '../core/auth.guard';

export const dashboardRoutes: Routes = [
  { path: '', component: DashboardHomeComponent, canActivate: [authGuard] }
];


