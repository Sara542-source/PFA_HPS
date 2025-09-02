import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgChartsModule } from 'ng2-charts';
import { ChartConfiguration, ChartOptions } from 'chart.js';


interface Transaction {
  transactionId: string;
  timestamp: string;
  creqReceptionMs: number;
  challengeDisplayMs: number;
  rreqSendingMs: number;
}


@Component({
  selector: 'app-pie-chart',
  imports: [ CommonModule, NgChartsModule ],
  templateUrl: './pie-chart.component.html',
  styleUrl: './pie-chart.component.css'
})

export class PieChartComponent {
  @Input() transactions: Transaction[] = [];

  pieChartData: ChartConfiguration<'doughnut'>['data'] = {
    labels: ['creqReceptionMs', 'challengeDisplayMs', 'rreqSendingMs'],
    datasets: [{
      data: [0, 0, 0],
      backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56']
    }]
  };

  pieChartOptions: ChartOptions<'doughnut'> = {
    responsive: true,
    plugins: {
      legend: { display: true, position: 'top' },
      tooltip: { enabled: true }
    }
  };

  ngOnChanges() {
    if (this.transactions.length > 0) {
      this.updateChartData();
    }
  }

  updateChartData() {
    const creqSum = this.transactions.reduce((sum, t) => sum + t.creqReceptionMs, 0);
    const challengeSum = this.transactions.reduce((sum, t) => sum + t.challengeDisplayMs, 0);
    const rreqSum = this.transactions.reduce((sum, t) => sum + t.rreqSendingMs, 0);

    this.pieChartData = {
      labels: ['creqReceptionMs', 'challengeDisplayMs', 'rreqSendingMs'],
      datasets: [{
        data: [creqSum, challengeSum, rreqSum],
        backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56']
      }]
    };
  }

}
