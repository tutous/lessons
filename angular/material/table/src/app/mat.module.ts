import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
// material
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
// app
import { PersonsService } from './persons.service';
import { MatTable } from './mat.table';

@NgModule({
  declarations: [
    MatTable
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    // material
    MatInputModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatProgressSpinnerModule
  ],
  providers: [PersonsService],
  bootstrap: [MatTable]
})
export class MatModule { }
