import { PersonStoreModule } from "./person/store/person.store.module";
import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";

import { AppComponent } from "./app.component";
import { PersonModule } from "./person/person.module";
import { StoreModule } from "@ngrx/store";
import { APP_REDUCER_KEY, AppReducer } from "./app.reducer";

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    StoreModule.forRoot({ APP_REDUCER_KEY: AppReducer }),
    PersonModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
