import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ThemeService } from '../theme.service';

interface Transaction {
  transactionId: string;
  timestamp: string;
  creqReceptionMs: number;
  challengeDisplayMs: number;
  rreqSendingMs: number;
}

@Component({
  selector: 'app-transaction-table',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './transaction-table.component.html',
  styleUrls: ['./transaction-table.component.css']
})
export class TransactionTableComponent implements OnInit {
  @Input() transactions: Transaction[] = [];
  currentTheme: string = 'light';

  currentPage: number = 1;
  pageSize: number = 10;
  totalPages: number = 1;

  constructor(private themeService: ThemeService) {}

  ngOnInit() {
    this.themeService.theme$.subscribe(theme => {
      this.currentTheme = theme;
    });
    this.updatePagination();
  }

  ngOnChanges() {
    console.log('TransactionTableComponent: Mise à jour des transactions:', this.transactions);
    this.updatePagination();
  }

  updatePagination() {
    this.totalPages = Math.ceil(this.transactions.length / this.pageSize);
    this.currentPage = Math.min(this.currentPage, this.totalPages || 1);
  }

  get paginatedTransactions(): Transaction[] {
    const start = (this.currentPage - 1) * this.pageSize;
    const end = start + this.pageSize;
    return this.transactions.slice(start, end);
  }

  goToPage(page: number) {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
    }
  }

  previousPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
    }
  }

  nextPage() {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
    }
  }
}