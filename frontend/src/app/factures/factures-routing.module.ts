import { Routes } from '@angular/router';
import { FactureListComponent } from './facture-list/facture-list.component';
import { FactureDetailsComponent } from './facture-details/facture-details.component';
import { FactureCreateComponent } from './facture-create/facture-create.component';
import { authGuard } from '../core/auth.guard';

export const facturesRoutes: Routes = [
  { path: '', component: FactureListComponent, canActivate: [authGuard] },
  { path: 'new', component: FactureCreateComponent, canActivate: [authGuard] },
  { path: ':id', component: FactureDetailsComponent, canActivate: [authGuard] }
];


