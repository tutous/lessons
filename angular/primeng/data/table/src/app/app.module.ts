import { BrowserModule } from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import {TableModule} from 'primeng/table';

import { CarService } from './service/car.service';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { AppTableSimpleModule } from './simple/app-table-simple.module';

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
    AppTableSimpleModule
  ],
  providers: [CarService],
  bootstrap: [AppComponent]
})
export class AppModule { }
