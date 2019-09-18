import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AppTableComponent } from './app-table.component';
import { TableModule } from 'primeng/table';
import { AppTableRoutingModule } from './app-table-routing.module';



@NgModule({
  declarations: [AppTableComponent],
  imports: [
    CommonModule,
    TableModule
  ],
  exports: [
    AppTableRoutingModule
  ]
})
export class AppTableColgrpModule { }
