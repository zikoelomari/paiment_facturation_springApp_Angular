import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import {
  Paiement,
  PaiementRequest,
  PaiementResponse
} from '../models/paiement.model';

@Injectable({
  providedIn: 'root'
})
export class PaiementService {
  private apiUrl = `${environment.apiUrl}/api/paiements`;

  constructor(private http: HttpClient) {}

  getPaiements(): Observable<PaiementResponse[]> {
    return this.http.get<PaiementResponse[]>(this.apiUrl);
  }

  getPaiement(id: number): Observable<PaiementResponse> {
    return this.http.get<PaiementResponse>(`${this.apiUrl}/${id}`);
  }

  getPaiementsByFacture(factureId: number): Observable<PaiementResponse[]> {
    return this.http.get<PaiementResponse[]>(`${this.apiUrl}/facture/${factureId}`);
  }

  processPaiement(request: PaiementRequest): Observable<PaiementResponse> {
    return this.http.post<PaiementResponse>(this.apiUrl, request);
  }
}


