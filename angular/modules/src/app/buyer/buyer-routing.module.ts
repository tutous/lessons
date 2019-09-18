import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BuyerCreateComponent } from './buyer-create.component';
import { BuyerCreateEuropeComponent } from './child/buyer-create-europe.component';
import { BuyerCreateAmericaComponent } from './child/buyer-create-america.component';
import { BuyerUpdateComponent } from './buyer-update.component';
import { BuyerUpdateEuropeComponent } from './child/buyer-update-europe.component';
import { BuyerUpdateAmericaComponent } from './child/buyer-update-america.component';

const routes: Routes = [
  { path: '', redirectTo: 'create', pathMatch: 'full' },
  {
    path: 'create', component: BuyerCreateComponent, children: [
      { path: 'europe/:location', component: BuyerCreateEuropeComponent },
      { path: 'america', component: BuyerCreateAmericaComponent }]
  },
  {
    path: 'update', component: BuyerUpdateComponent, children: [
      { path: 'europe', component: BuyerUpdateEuropeComponent },
      { path: 'america', component: BuyerUpdateAmericaComponent }]
  },
  { path: '**', redirectTo: 'create', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BuyerRoutingModule { }
