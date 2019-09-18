import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Person } from './person';

@Injectable({
  providedIn: 'root'
})
export class PersonService {

  constructor() { }

  getPersons(): Observable<Person[]> {
    return of([
      { foreName: 'Uwe', name: 'Sluga', city: 'Wob' },
      { foreName: 'Anton', name: 'Meier', city: 'Hamburg' }]
    );
  }

}
