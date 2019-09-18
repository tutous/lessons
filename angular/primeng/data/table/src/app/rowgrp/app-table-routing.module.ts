import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppTableComponent } from './app-table.component';

const routes: Routes = [
  { path: 'rowgrp', component: AppTableComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppTableRoutingModule { }
