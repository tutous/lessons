import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";

import { AppComponent } from "./app.component";
import { StoreModule } from "@ngrx/store";
import { PersonState } from "./person.state";
import { reducer } from "./person.reducer";
import { PersonCreateComponent } from "./person.create.component";
import { PersonReadComponent } from "./person.read.component";

@NgModule({
  declarations: [AppComponent, PersonCreateComponent, PersonReadComponent],
  imports: [
    BrowserModule,
    StoreModule.forRoot({
      persons: reducer
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
