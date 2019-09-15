import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';


import { BindingComponent } from './binding.component';
import { HelloComponent } from './hello/hello.component';

@NgModule({
  declarations: [
    BindingComponent,
    HelloComponent
  ],
  imports: [
    BrowserModule,
    FormsModule
  ],
  providers: []
})
export class BindingModule { }
