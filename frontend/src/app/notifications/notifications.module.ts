import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { notificationsRoutes } from './notifications-routing.module';
import { NotificationListComponent } from './notification-list/notification-list.component';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forChild(notificationsRoutes),
    NotificationListComponent
  ]
})
export class NotificationsModule { }


