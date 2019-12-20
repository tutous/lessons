import { Component, OnInit } from "@angular/core";
import { Store } from "@ngrx/store";
import { AppState } from "./app.state";
import { Observable, of } from "rxjs";
import { APP_REDUCER_KEY } from "./app.reducer";

@Component({
  selector: "app-root",
  template: `
    Person Size is {{ personSize | async }}
    <app-person></app-person>
  `,
  styles: []
})
export class AppComponent implements OnInit {
  title = "getting-startet";
  personSize: Observable<number>;

  constructor(private store: Store<AppState>) {}

  ngOnInit() {
    // get personReducer and read the personState
    this.store.select(APP_REDUCER_KEY).subscribe(
      appState =>
        // create a Observable based on the state
        (this.personSize = of(appState.personState.persons.size))
    );
  }
}
