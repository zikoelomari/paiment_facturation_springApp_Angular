import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { facturesRoutes } from './factures-routing.module';
import { FactureListComponent } from './facture-list/facture-list.component';
import { FactureDetailsComponent } from './facture-details/facture-details.component';
import { FactureCreateComponent } from './facture-create/facture-create.component';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forChild(facturesRoutes),
    ReactiveFormsModule,
    FactureListComponent,
    FactureDetailsComponent,
    FactureCreateComponent
  ]
})
export class FacturesModule { }


