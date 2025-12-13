import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { clientsRoutes } from './clients-routing.module';
import { ClientListComponent } from './client-list/client-list.component';
import { ClientDetailsComponent } from './client-details/client-details.component';
import { ClientFormComponent } from './client-form/client-form.component';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forChild(clientsRoutes),
    ReactiveFormsModule,
    ClientListComponent,
    ClientDetailsComponent,
    ClientFormComponent
  ]
})
export class ClientsModule { }


