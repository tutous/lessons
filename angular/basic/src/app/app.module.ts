import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BindingComponent } from './binding/binding.component';
import { HelloComponent } from './binding/hello/hello.component';
import { StructuralComponent } from './directive/structural/structural.component';
import { DataComponent } from './directive/structural/table-data.component';

@NgModule({
  declarations: [
    AppComponent,
    BindingComponent,
    HelloComponent,
    StructuralComponent,
    DataComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
