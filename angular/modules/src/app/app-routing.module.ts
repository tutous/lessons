import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import routeToBuyer from './app-routing.util';

const routes: Routes = [
  {
    path: 'buyer', loadChildren: () => import('./buyer/buyer.module').then(m => m.BuyerModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
