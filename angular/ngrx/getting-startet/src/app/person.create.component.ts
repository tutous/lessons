import { Component, OnInit } from "@angular/core";
import { Store } from "@ngrx/store";
import { PersonState } from "./person.state";
import * as PersonActions from "./person.actions";

@Component({
  selector: "person-create",
  template: `
    <div>Person anlegen</div>
    <input type="text" placeholder="Name" #name />
    <input type="text" placeholder="Vorname" #foreName />
    <input type="text" placeholder="Stadt" #city />
    <button (click)="create(name.value, foreName.value, city.value)">
      Add
    </button>
  `,
  styles: [""]
})
export class PersonCreateComponent implements OnInit {
  constructor(private store: Store<PersonState>) {}

  ngOnInit() {}

  create(name: string, foreName: string, city: string) {
    this.store.dispatch(
      new PersonActions.AddPerson({
        name: name,
        foreName: foreName,
        city: city
      })
    );
  }
}
