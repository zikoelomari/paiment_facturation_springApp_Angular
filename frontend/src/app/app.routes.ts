import { Routes } from '@angular/router';
import { roleGuard } from './core/guards/role.guard';

export const routes: Routes = [
  {
    path: 'auth',
    loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule)
  },
  {
    path: 'dashboard',
    loadChildren: () => import('./dashboard/dashboard.module').then(m => m.DashboardModule),
    canActivate: [roleGuard],
    data: { roles: ['ROLE_USER', 'ROLE_ADMIN'] }
  },
  {
    path: 'admin',
    loadComponent: () => import('./admin/admin-home/admin-home.component').then(c => c.AdminHomeComponent),
    canActivate: [roleGuard],
    data: { roles: ['ROLE_ADMIN'] }
  },
  {
    path: 'clients',
    loadChildren: () => import('./clients/clients.module').then(m => m.ClientsModule),
    canActivate: [roleGuard],
    data: { roles: ['ROLE_USER', 'ROLE_ADMIN'] }
  },
  {
    path: 'factures',
    loadChildren: () => import('./factures/factures.module').then(m => m.FacturesModule),
    canActivate: [roleGuard],
    data: { roles: ['ROLE_USER', 'ROLE_ADMIN'] }
  },
  {
    path: 'paiements',
    loadChildren: () => import('./paiements/paiements.module').then(m => m.PaiementsModule),
    canActivate: [roleGuard],
    data: { roles: ['ROLE_USER', 'ROLE_ADMIN'] }
  },
  {
    path: 'notifications',
    loadChildren: () => import('./notifications/notifications.module').then(m => m.NotificationsModule),
    canActivate: [roleGuard],
    data: { roles: ['ROLE_USER', 'ROLE_ADMIN'] }
  },
  { path: 'login', redirectTo: '/auth/login', pathMatch: 'full' },
  { path: 'register', redirectTo: '/auth/register', pathMatch: 'full' },
  { path: '', pathMatch: 'full', redirectTo: '/dashboard' },
  { path: '**', redirectTo: '/dashboard', pathMatch: 'full' }
];
