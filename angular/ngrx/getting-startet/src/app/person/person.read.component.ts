import { Component, OnInit } from "@angular/core";
import { Observable, of } from 'rxjs';
import { Store } from "@ngrx/store";

import * as PersonActions from "./store/person.actions";
import { PersonState } from './store/person.state';
import { Person } from "./store/person";

@Component({
  selector: "person-read",
  template: `
    <div>Persons</div>
    <div *ngIf="persons">
      <div *ngFor="let person of persons | async">
        <span style="padding:10px">{{person.foreName}}</span>
        <span style="padding:10px">{{person.name}}</span>
        <span style="padding:10px">{{person.city}}</span>
      </div>
    </div>
  `,
  styles: [""]
})
export class PersonReadComponent implements OnInit {

  persons: Observable<Person[]>;

  constructor(private store: Store<PersonState>) { }

  ngOnInit() {
    // get personReducer and read the personState
    this.store.select('personReducer')
      .subscribe(personState =>
        // create a Observable based on the state
        this.persons = of(personState.persons));
    // trigger PersonEffects as action 
    this.store.dispatch(new PersonActions.LoadPersonsAction());
  }

}
