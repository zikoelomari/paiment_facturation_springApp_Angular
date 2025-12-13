import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import {
  Client,
  ClientRequest,
  ClientUpdate,
  ClientResponse,
  LoginRequest,
  AuthResponse
} from '../models/client.model';

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  private apiUrl = `${environment.apiUrl}/api/clients`;

  constructor(private http: HttpClient) {}

  // Auth methods
  login(credentials: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, credentials);
  }

  register(client: ClientRequest): Observable<ClientResponse> {
    return this.http.post<ClientResponse>(`${this.apiUrl}/register`, client);
  }

  // Client CRUD operations
  getClients(): Observable<ClientResponse[]> {
    return this.http.get<ClientResponse[]>(this.apiUrl);
  }

  getClient(id: number): Observable<ClientResponse> {
    return this.http.get<ClientResponse>(`${this.apiUrl}/${id}`);
  }

  getClientByEmail(email: string): Observable<ClientResponse> {
    return this.http.get<ClientResponse>(`${this.apiUrl}/email/${email}`);
  }

  searchClientsByName(name: string): Observable<ClientResponse[]> {
    return this.http.get<ClientResponse[]>(`${this.apiUrl}/search/name?name=${name}`);
  }

  createClient(client: ClientRequest): Observable<ClientResponse> {
    return this.http.post<ClientResponse>(this.apiUrl, client);
  }

  updateClient(id: number, client: ClientUpdate): Observable<ClientResponse> {
    return this.http.put<ClientResponse>(`${this.apiUrl}/${id}`, client);
  }

  deleteClient(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  // Helper methods
  getClientStatuses(): string[] {
    return Object.values(['ACTIVE', 'INACTIVE', 'SUSPENDED']);
  }
}


