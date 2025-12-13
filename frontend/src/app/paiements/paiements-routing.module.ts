import { Routes } from '@angular/router';
import { PaiementFormComponent } from './paiement-form/paiement-form.component';
import { PaiementHistoryComponent } from './paiement-history/paiement-history.component';
import { authGuard } from '../core/auth.guard';

export const paiementsRoutes: Routes = [
  { path: '', component: PaiementHistoryComponent, canActivate: [authGuard] },
  { path: 'new', component: PaiementFormComponent, canActivate: [authGuard] }
];


