import { PersonEffects } from './person.effects';
import { StoreModule } from "@ngrx/store";
import { PersonReducer } from "./person.reducer";
import { EffectsModule } from '@ngrx/effects';
import { NgModule } from '@angular/core';

@NgModule({
  imports: [
    StoreModule.forRoot({
      personReducer: PersonReducer
    }),
    EffectsModule.forRoot([
      PersonEffects
    ]),
  ]
})
export class PersonStoreModule { }
