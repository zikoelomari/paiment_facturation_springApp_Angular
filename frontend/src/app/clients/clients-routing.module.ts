import { Routes } from '@angular/router';
import { ClientListComponent } from './client-list/client-list.component';
import { ClientDetailsComponent } from './client-details/client-details.component';
import { ClientFormComponent } from './client-form/client-form.component';
import { authGuard } from '../core/auth.guard';

export const clientsRoutes: Routes = [
  { path: '', component: ClientListComponent, canActivate: [authGuard] },
  { path: 'new', component: ClientFormComponent, canActivate: [authGuard] },
  { path: ':id', component: ClientDetailsComponent, canActivate: [authGuard] },
  { path: ':id/edit', component: ClientFormComponent, canActivate: [authGuard] }
];


