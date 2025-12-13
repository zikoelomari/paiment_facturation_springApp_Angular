import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { paiementsRoutes } from './paiements-routing.module';
import { PaiementFormComponent } from './paiement-form/paiement-form.component';
import { PaiementHistoryComponent } from './paiement-history/paiement-history.component';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forChild(paiementsRoutes),
    ReactiveFormsModule,
    PaiementFormComponent,
    PaiementHistoryComponent
  ]
})
export class PaiementsModule { }


