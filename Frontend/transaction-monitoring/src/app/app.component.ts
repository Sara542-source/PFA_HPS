import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { RouterModule } from '@angular/router';
import { FilterBarComponent } from './filter-bar/filter-bar.component';
import { HttpClientModule } from '@angular/common/http';
import { LineChartComponent } from './line-chart/line-chart.component';
import { NgChartsModule } from 'ng2-charts';
import { CommonModule } from '@angular/common';
import { PieChartComponent } from './pie-chart/pie-chart.component';
import { TransactionTableComponent } from './transaction-table/transaction-table.component';



@Component({
  selector: 'app-root',
  imports: [RouterOutlet, NavBarComponent, RouterModule, FilterBarComponent, HttpClientModule, LineChartComponent, NgChartsModule, CommonModule, PieChartComponent, TransactionTableComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'transaction-monitoring';
  filteredData: any[] = [];

  onFilterChange(data: any[]) {
    this.filteredData = data;
    console.log('Données filtrées:', this.filteredData); // Pour débogage
  }
}
