import { PersonStoreModule } from './store/person.store.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PersonComponent } from './person.component';
import { PersonCreateComponent } from './person.create.component';
import { PersonReadComponent } from './person.read.component';

@NgModule({
  imports: [
    CommonModule
  ],
  exports: [
    PersonComponent,
    PersonStoreModule
  ],
  declarations: [
    PersonComponent,
    PersonCreateComponent,
    PersonReadComponent
  ]
})
export class PersonModule { }
