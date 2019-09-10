import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AppTableRoutingModule } from './app-table-routing.module';
import { AppTableComponent } from './app-table.component';
import { TableModule } from 'primeng/table';



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
export class AppTableSimpleModule { }
