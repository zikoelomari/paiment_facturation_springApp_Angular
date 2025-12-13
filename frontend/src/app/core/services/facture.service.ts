import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import {
  Facture,
  FactureRequest,
  FactureUpdate,
  FactureResponse,
  FactureStatut
} from '../models/facture.model';

@Injectable({
  providedIn: 'root'
})
export class FactureService {
  private apiUrl = `${environment.apiUrl}/api/factures`;

  constructor(private http: HttpClient) {}

  getFactures(): Observable<FactureResponse[]> {
    return this.http.get<FactureResponse[]>(this.apiUrl);
  }

  getFacture(id: number): Observable<FactureResponse> {
    return this.http.get<FactureResponse>(`${this.apiUrl}/${id}`);
  }

  getFacturesByClient(clientId: number): Observable<FactureResponse[]> {
    return this.http.get<FactureResponse[]>(`${this.apiUrl}/client/${clientId}`);
  }

  getOverdueFactures(): Observable<FactureResponse[]> {
    return this.http.get<FactureResponse[]>(`${this.apiUrl}/overdue`);
  }

  getFacturesByStatus(status: FactureStatut): Observable<FactureResponse[]> {
    return this.http.get<FactureResponse[]>(`${this.apiUrl}/status/${status}`);
  }

  createFacture(facture: FactureRequest): Observable<FactureResponse> {
    return this.http.post<FactureResponse>(this.apiUrl, facture);
  }

  updateFacture(id: number, facture: FactureUpdate): Observable<FactureResponse> {
    return this.http.put<FactureResponse>(`${this.apiUrl}/${id}`, facture);
  }

  markAsPaid(id: number): Observable<void> {
    return this.http.patch<void>(`${this.apiUrl}/${id}/paid`, {});
  }

  deleteFacture(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}

