import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';
import { StorageUtil } from '../utils/storage.util';

export interface JwtPayload {
  sub: string;
  roles?: string;
  exp?: number;
  iat?: number;
  [key: string]: any;
}

@Injectable({
  providedIn: 'root'
})
export class JwtService {
  
  decodeToken(token: string): JwtPayload | null {
    try {
      return jwtDecode<JwtPayload>(token);
    } catch (error) {
      console.error('Error decoding token:', error);
      return null;
    }
  }

  getToken(): string | null {
    return StorageUtil.getToken();
  }

  getDecodedToken(): JwtPayload | null {
    const token = this.getToken();
    if (!token) {
      return null;
    }
    return this.decodeToken(token);
  }

  getUsername(): string | null {
    const decoded = this.getDecodedToken();
    return decoded?.sub || null;
  }

  getRoles(): string[] {
    const decoded = this.getDecodedToken();
    if (!decoded?.roles) {
      return [];
    }
    return decoded.roles.split(',').map(role => role.trim());
  }

  hasRole(role: string): boolean {
    const roles = this.getRoles();
    return roles.includes(role);
  }

  hasAnyRole(roles: string[]): boolean {
    const userRoles = this.getRoles();
    return roles.some(role => userRoles.includes(role));
  }

  isTokenExpired(): boolean {
    const decoded = this.getDecodedToken();
    if (!decoded?.exp) {
      return true;
    }
    const expirationDate = new Date(decoded.exp * 1000);
    return expirationDate < new Date();
  }

  isTokenValid(): boolean {
    const token = this.getToken();
    if (!token) {
      return false;
    }
    return !this.isTokenExpired();
  }
}


