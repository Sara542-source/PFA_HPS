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
  selector: 'app-line-chart',
  standalone: true,
  imports: [CommonModule, NgChartsModule],
  templateUrl: './line-chart.component.html',
  styleUrls: ['./line-chart.component.css']
})
export class LineChartComponent {
  @Input() transactions: Transaction[] = [];

  lineChartData: ChartConfiguration<'line'>['data'] = {
    labels: [],
    datasets: [
      {
        data: [],
        label: 'creqReceptionMs',
        borderColor: '#FF6384',
        pointBackgroundColor: [],
        pointRadius: []
      },
      {
        data: [],
        label: 'challengeDisplayMs',
        borderColor: '#36A2EB',
        pointBackgroundColor: [],
        pointRadius: []
      },
      {
        data: [],
        label: 'rreqSendingMs',
        borderColor: '#FFCE56',
        pointBackgroundColor: [],
        pointRadius: []
      }
    ]
  };

  lineChartOptions: ChartOptions<'line'> = {
    responsive: true,
    scales: {
      x: { title: { display: true, text: 'Timestamp' } },
      y: { title: { display: true, text: 'Milliseconds' }, beginAtZero: true }
    },
    plugins: {
      legend: { display: true },
      tooltip: { enabled: true }
    }
  };

  ngOnChanges() {
    if (this.transactions.length > 0) {
      this.updateChartData();
    }
  }

  updateChartData() {
    const labels = this.transactions.map(t => new Date(t.timestamp).toLocaleString());
    const creqData = this.transactions.map(t => t.creqReceptionMs);
    const challengeData = this.transactions.map(t => t.challengeDisplayMs);
    const rreqData = this.transactions.map(t => t.rreqSendingMs);
    const creqPointColors = this.transactions.map(t => t.creqReceptionMs > 100 ? '#FF0000' : '#FF6384');
    const challengePointColors = this.transactions.map(t => t.challengeDisplayMs > 100 ? '#FF0000' : '#36A2EB');
    const rreqPointColors = this.transactions.map(t => t.rreqSendingMs > 100 ? '#FF0000' : '#FFCE56');
    const pointRadius = this.transactions.map(t => (t.creqReceptionMs > 100 || t.challengeDisplayMs > 100 || t.rreqSendingMs > 100) ? 6 : 3);

    this.lineChartData = {
      labels: labels,
      datasets: [
        {
          data: creqData,
          label: 'creqReceptionMs',
          borderColor: '#FF6384',
          pointBackgroundColor: creqPointColors,
          pointRadius: pointRadius
        },
        {
          data: challengeData,
          label: 'challengeDisplayMs',
          borderColor: '#36A2EB',
          pointBackgroundColor: challengePointColors,
          pointRadius: pointRadius
        },
        {
          data: rreqData,
          label: 'rreqSendingMs',
          borderColor: '#FFCE56',
          pointBackgroundColor: rreqPointColors,
          pointRadius: pointRadius
        }
      ]
    };
  }
}