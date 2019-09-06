import { PERSONS } from './repository/persons';
import { Injectable } from '@angular/core';
import { Observable, from } from 'rxjs';
import { Person } from './domain/person';
import { filter, toArray, map, flatMap } from 'rxjs/operators';
import { filterPerson } from './persons.filter';

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

  findAllPersons(nameFilter: string, foreNameFilter: string, cityFilter: string,
    sortColumn: string, sortDirection: string,
    pageIndex: number, pageSize: number): Observable<Person> {

    const startIndex: number = pageIndex * pageSize;
    const endIndex: number = startIndex + pageSize - 1;
    let index = -1;

    return this.persons.pipe(
      filter(person => this.isPerson(person, nameFilter, foreNameFilter, cityFilter)),
      toArray(), map(persons => this.sort(persons, sortColumn, sortDirection)), flatMap(p => p),
      filter(person => ++index >= startIndex && index <= endIndex));
  }

  private isPerson(person: Person, nameFilter: string, foreNameFilter: string, cityFilter: string) {
    return (nameFilter.length > 0 ? person.name.toUpperCase().indexOf(nameFilter.toUpperCase()) >= 0 : true) &&
      (foreNameFilter.length > 0 ? person.foreName.toUpperCase().indexOf(foreNameFilter.toUpperCase()) >= 0 : true) &&
      (cityFilter.length > 0 ? person.city.toUpperCase().indexOf(cityFilter.toUpperCase()) >= 0 : true);
  }

  private sort(persons: Person[], sortColumn: string, sortDirection: string): Person[] {
    if ('Name' === sortColumn) {
      return persons.sort((p1, p2) => sortDirection === 'asc' ?
        p1.name.localeCompare(p2.name) : p2.name.localeCompare(p1.name));
    } else if ('Vorname' === sortColumn) {
      return persons.sort((p1, p2) => sortDirection === 'asc' ?
        p1.foreName.localeCompare(p2.foreName) : p2.foreName.localeCompare(p1.foreName));
    }
    return persons;
  }
}
