import { PERSONS } from './repository/persons';
import { Injectable } from '@angular/core';
import { Observable, from } from 'rxjs';
import { Person } from './domain/person';

@Injectable({
  providedIn: 'root'
})
export class PersonsService {

  private persons: Observable<Person>;

  constructor() {
    // Aussteller von Abos
    // this.persons = from(PERSONS);
    this.persons = new Observable<Person>(susbriber => {
      PERSONS.forEach(p => susbriber.next(p), 500);
      setTimeout(() => susbriber.complete(), 1000);
    });
  }

  findAllPersons(): Observable<Person> {
    return this.persons;
  }
}
