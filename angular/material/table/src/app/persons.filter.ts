import { Person } from './domain/person';
import { MonoTypeOperatorFunction, Observable, OperatorFunction, BehaviorSubject, Subscriber } from 'rxjs';
import { Predicate } from '@angular/core';
export const filterPerson: (nameFilter: string, foreNameFilter: string, cityFilter: string) => OperatorFunction<Person, Person> =
  function (nameFilter: string, foreNameFilter: string, cityFilter: string): OperatorFunction<Person, Person> {
    return inputObservable => {
      return new Observable<Person>(subscriber => {
        inputObservable.subscribe(p => {
          const found = (nameFilter.length > 0 ? p.name.toUpperCase().indexOf(nameFilter.toUpperCase()) >= 0 : true) &&
            (foreNameFilter.length > 0 ? p.foreName.toUpperCase().indexOf(foreNameFilter.toUpperCase()) >= 0 : true) &&
            (cityFilter.length > 0 ? p.city.toUpperCase().indexOf(cityFilter.toUpperCase()) >= 0 : true);
          if (found) {
            subscriber.next(p);
          }
        },
          err => subscriber.error(err),
          () =>
            subscriber.complete()
        );
      });
    };
  };
