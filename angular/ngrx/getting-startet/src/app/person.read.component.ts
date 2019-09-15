import { Component, OnInit } from "@angular/core";
import { Observable } from "rxjs";
import { Person } from "./person";
import { Store } from "@ngrx/store";
import { PersonState } from "./person.state";

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

  constructor(private store: Store<PersonState>) {}

  ngOnInit() {
    this.persons = this.store.select("persons");
  }
}
