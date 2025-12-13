export enum ClientStatus {
  ACTIVE = 'ACTIVE',
  INACTIVE = 'INACTIVE',
  SUSPENDED = 'SUSPENDED'
}

export interface Client {
  id?: number;
  email: string;
  firstName: string;
  lastName: string;
  password?: string;
  roles?: string[];
  status: ClientStatus;
  createdAt?: string;
  updatedAt?: string;
}

export interface ClientRequest {
  email: string;
  firstName: string;
  lastName: string;
  password: string;
  status?: ClientStatus;
}

export interface ClientUpdate {
  email?: string;
  firstName?: string;
  lastName?: string;
  status?: ClientStatus;
}

export interface ClientResponse {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  roles?: string[];
  status: ClientStatus;
  createdAt?: string;
  updatedAt?: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface AuthResponse {
  token: string;
  email: string;
}

