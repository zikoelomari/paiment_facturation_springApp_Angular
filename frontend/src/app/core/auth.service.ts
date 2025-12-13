import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { StorageUtil } from './utils/storage.util';
import { JwtService } from './services/jwt.service';
import { ClientService } from './services/client.service';
import { LoginRequest, AuthResponse } from './models/client.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private http = inject(HttpClient);
  private router = inject(Router);
  private jwtService = inject(JwtService);
  private clientService = inject(ClientService);

  readonly isAuthenticated = signal<boolean>(this.checkAuthentication());
  readonly currentUser = signal<any>(null);

  constructor() {
    this.loadUserFromToken();
  }

  login(credentials: LoginRequest): Observable<AuthResponse> {
    return this.clientService.login(credentials).pipe(
      tap((response) => {
        StorageUtil.setToken(response.token);
        this.isAuthenticated.set(true);
        this.loadUserFromToken();
        this.router.navigate(['/dashboard']);
      })
    );
  }

  register(data: any): Observable<any> {
    return this.clientService.register(data).pipe(
      tap(() => {
        // After registration, optionally auto-login
      })
    );
  }

  logout(): void {
    StorageUtil.clear();
    this.isAuthenticated.set(false);
    this.currentUser.set(null);
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return StorageUtil.getToken();
  }

  getUsername(): string | null {
    return this.jwtService.getUsername();
  }

  getRoles(): string[] {
    return this.jwtService.getRoles();
  }

  hasRole(role: string): boolean {
    return this.jwtService.hasRole(role);
  }

  hasAnyRole(roles: string[]): boolean {
    return this.jwtService.hasAnyRole(roles);
  }

  isLoggedIn(): boolean {
    return this.isAuthenticated() && this.jwtService.isTokenValid();
  }

  private checkAuthentication(): boolean {
    const token = StorageUtil.getToken();
    if (!token) {
      return false;
    }
    return this.jwtService.isTokenValid();
  }

  private loadUserFromToken(): void {
    const token = this.getToken();
    if (token && this.jwtService.isTokenValid()) {
      const decoded = this.jwtService.getDecodedToken();
      if (decoded) {
        this.currentUser.set({
          username: decoded.sub,
          roles: this.getRoles()
        });
      }
    }
  }
}

