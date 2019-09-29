import { Component, OnInit } from "@angular/core";
import { Store, props } from "@ngrx/store";
import { PersonState } from "./store/person.state";
import { addPerson } from "./store/person.actions";

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
      addPerson({ person: { name: "Sluga", foreName: "Uwe", city: "Wob" } })
    );
  }
}
