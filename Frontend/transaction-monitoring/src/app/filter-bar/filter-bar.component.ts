import { Component } from '@angular/core';
import { Output, EventEmitter } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { OnInit , OnDestroy } from '@angular/core';
import { Subject, takeUntil } from 'rxjs';


interface Transaction {
  transactionId: string;
  timestamp: string;
  creqReceptionMs: number;
  challengeDisplayMs: number;
  rreqSendingMs: number;
}

@Component({
  selector: 'app-filter-bar',
  imports: [FormsModule, CommonModule],
  templateUrl: './filter-bar.component.html',
  styleUrl: './filter-bar.component.css'
})
export class FilterBarComponent implements OnInit , OnDestroy {
    startDateTime:string ='';
    endDateTime:string ='';
    private ws: WebSocket | null = null;
    private destroy$ = new Subject<void>();
  

    @Output() filterChange = new EventEmitter<any[]>();
    constructor(private http: HttpClient) {}
    
    ngOnInit() {
  console.log('FilterBarComponent initialized');
  this.fetchLastTransactions();
  this.connectWebSocket();
    
}
ngOnDestroy() {
      this.destroy$.next();
    this.destroy$.complete();
    if (this.ws) {
      this.ws.close();
      console.log('Connexion WebSocket fermée');
    }
    }

      connectWebSocket() {
    this.ws = new WebSocket('ws://localhost:9097/transaction-monitor/ws');
    this.ws.onmessage = (event) => {
      const newTransaction: Transaction = JSON.parse(event.data);
      console.log('Nouvelle transaction reçue via WebSocket:', newTransaction);
      // Ajouter uniquement si aucun filtre de date n'est appliqué
      if (!this.startDateTime || !this.endDateTime) {
        this.fetchLastTransactions();
      }
    };
    this.ws.onerror = (error) => {
      console.error('Erreur WebSocket:', error);
    };
    this.ws.onopen = () => {
      console.log('Connexion WebSocket établie');
    };
  }


fetchLastTransactions() {
  console.log('Fetching last transactions');
  this.http.get<any[]>('http://localhost:9097/transaction-monitor/api/transactions',{params: { limit : '10'}}).subscribe({
    next: (data) => { 
      console.log('Données récupérées:', data);
      const lastTen = data
            .sort((a, b) => new Date(b.timestamp).getTime() - new Date(a.timestamp).getTime())
            .slice(0, 10);
          console.log('10 dernières transactions triées:', lastTen);
          this.filterChange.emit(lastTen);
    },
    error: (error) => {
      console.error('Error fetching last transactions:', error);
      this.filterChange.emit([]);
    }

})
}
    applyFilter() {
      if(this.startDateTime && this.endDateTime) {
      // Convertir datetime-local (YYYY-MM-DDTHH:mm) en YYYY-MM-DD HH:mm:ss.SSS
      const start = new Date(this.startDateTime);
      const end = new Date(this.endDateTime);
      const formattedStartDate = `${start.getFullYear()}-${(start.getMonth() + 1).toString().padStart(2, '0')}-${start.getDate().toString().padStart(2, '0')} ${start.getHours().toString().padStart(2, '0')}:${start.getMinutes().toString().padStart(2, '0')}:00.000`;
      const formattedEndDate = `${end.getFullYear()}-${(end.getMonth() + 1).toString().padStart(2, '0')}-${end.getDate().toString().padStart(2, '0')} ${end.getHours().toString().padStart(2, '0')}:${end.getMinutes().toString().padStart(2, '0')}:59.999`;
      this.http.get<any[]>('http://localhost:9097/transaction-monitor/api/transactions', {
        params: {
          startDate: formattedStartDate,
          endDate: formattedEndDate
        }
      }).subscribe({ 
        next: (data) => { 
        const sortedData = data.sort((a, b) => new Date(b.timestamp).getTime() - new Date(a.timestamp).getTime());
        this.filterChange.emit(sortedData);
        console.log('Données récupérées:', data);
      }
      ,error: (error) => {
        console.error('Error fetching filtered data:', error);
        this.filterChange.emit([]);
      }
      });

    }else {
      console.log('pas de data reçue') ;
      this.filterChange.emit([]);
    }
    }
  }
