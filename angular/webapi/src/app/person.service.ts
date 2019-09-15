import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Person } from './person';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { tap, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class PersonService {

  private url: string = '/api/persons';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  getPersons(): Observable<Person[]> {
    return this.http.get<Person[]>(this.url)
      .pipe(
        tap(_ => console.log('fetched Persons')),
        catchError(this.handleError<Person[]>('getPersons', []))
      );
  }

  getPerson(id: number): Observable<Person> {
    const url = `${this.url}/${id}`;
    return this.http.get<Person>(url).pipe(
      tap(_ => console.log(`fetched person id=${id}`)),
      catchError(this.handleError<Person>(`getPerson id=${id}`))
    );
  }

  findPersons(term: string): Observable<Person[]> {
    if (!term.trim()) {
      // if not search term, return empty Person array.
      return of([]);
    }
    return this.http.get<Person[]>(`${this.url}/?name=${term}`).pipe(
      tap(_ => console.log(`found persons matching "${term}"`)),
      catchError(this.handleError<Person[]>('findPersons', []))
    );
  }

  addPerson(person: Person): Observable<Person> {
    return this.http.post<Person>(this.url, person, this.httpOptions).pipe(
      tap((newPerson: Person) => console.log(`added Person w/ id=${newPerson.id}`)),
      catchError(this.handleError<Person>('addPerson'))
    );
  }

  deletePerson(person: Person | number): Observable<Person> {
    const id = typeof person === 'number' ? person : person.id;
    const url = `${this.url}/${id}`;

    return this.http.delete<Person>(url, this.httpOptions).pipe(
      tap(_ => console.log(`deleted Person id=${id}`)),
      catchError(this.handleError<Person>('deletePerson'))
    );
  }

  updatePerson(person: Person): Observable<any> {
    return this.http.put(this.url, person, this.httpOptions).pipe(
      tap(_ => console.log(`updated Person id=${person.id}`)),
      catchError(this.handleError<any>('updatePerson'))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(` ${operation}: ${error}`);
      return of(result as T);
    };
  }

}
