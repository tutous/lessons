import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { StructuralComponent } from './structural/structural.component';
import { DataComponent } from './structural/table-data.component';


@NgModule({
  declarations: [
    StructuralComponent,
    DataComponent
  ],
  imports: [
    BrowserModule,
    FormsModule
  ],
  providers: []
})
export class DirectiveModule { }
