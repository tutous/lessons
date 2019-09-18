import { AppTableColgrpModule } from './colgrp/app-table-colgrp.module';
import { BrowserModule } from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import {TableModule} from 'primeng/table';

import { CarService } from './service/car.service';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';

import { AppTableSimpleModule } from './simple/app-table-simple.module';
import { AppTableRowgrpModule } from './rowgrp/app-table-rowgrp.module';


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    // primeng
    TableModule,
    AppTableSimpleModule,
    AppTableColgrpModule,
    AppTableRowgrpModule
  ],
  providers: [CarService],
  bootstrap: [AppComponent]
})
export class AppModule { }
