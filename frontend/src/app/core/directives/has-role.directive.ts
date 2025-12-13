import { Directive, Input, TemplateRef, ViewContainerRef } from '@angular/core';
import { AuthService } from '../auth.service';

@Directive({
  selector: '[hasRole]',
  standalone: true
})
export class HasRoleDirective {
  private roles: string[] = [];

  constructor(
    private templateRef: TemplateRef<unknown>,
    private viewContainer: ViewContainerRef,
    private authService: AuthService
  ) {}

  @Input() set hasRole(role: string | string[]) {
    this.roles = Array.isArray(role) ? role : [role];
    this.updateView();
  }

  private updateView(): void {
    this.viewContainer.clear();
    if (this.authService.hasAnyRole(this.roles)) {
      this.viewContainer.createEmbeddedView(this.templateRef);
    }
  }
}
