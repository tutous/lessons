import { NgModule, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BuyerRoutingModule } from './buyer-routing.module';
import { BuyerCreateComponent } from './buyer-create.component';
import { BuyerCreateEuropeComponent } from './child/buyer-create-europe.component';
import { BuyerCreateAmericaComponent } from './child/buyer-create-america.component';
import { BuyerUpdateComponent } from './buyer-update.component';
import { BuyerUpdateEuropeComponent } from './child/buyer-update-europe.component';
import { BuyerUpdateAmericaComponent } from './child/buyer-update-america.component';



@NgModule({
  declarations: [
    BuyerCreateComponent,
    BuyerCreateEuropeComponent,
    BuyerCreateAmericaComponent,
    BuyerUpdateComponent,
    BuyerUpdateEuropeComponent,
    BuyerUpdateAmericaComponent,
  ],
  imports: [
    CommonModule,
    BuyerRoutingModule
  ],
  exports: [

  ]
})
export class BuyerModule {

  constructor() {
    console.log('BuyerModule is loaded.');
  }

}
