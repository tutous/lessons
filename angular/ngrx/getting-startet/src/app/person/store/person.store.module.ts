import { PersonEffects } from "./person.effects";
import { StoreModule } from "@ngrx/store";
import { PersonReducer, PERSON_FEATURE_KEY } from "./person.reducer";
import { EffectsModule } from "@ngrx/effects";
import { NgModule } from "@angular/core";

@NgModule({
  imports: [
    StoreModule.forFeature(PERSON_FEATURE_KEY, PersonReducer),
    EffectsModule.forRoot([PersonEffects])
  ]
})
export class PersonStoreModule {}
