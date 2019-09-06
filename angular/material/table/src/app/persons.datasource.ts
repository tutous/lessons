import { PersonsService } from './persons.service';
import { Person } from './domain/person';
import { CollectionViewer, DataSource } from '@angular/cdk/collections';
import { Observable, BehaviorSubject, of } from 'rxjs';
import { catchError, finalize, toArray } from 'rxjs/operators';

export class PersonsDataSource implements DataSource<Person> {

  private personsSubject = new BehaviorSubject<Person[]>([]);

  private loadingSubject = new BehaviorSubject<boolean>(false);

  public loading$ = this.loadingSubject.asObservable();

  constructor(private coursesService: PersonsService) {

  }

  loadAllPersons(pageIndex: number, pageSize: number) {

    this.loadingSubject.next(true);

    this.coursesService.findAllPersons().pipe(
      toArray(),
      catchError(() => of([])),
      finalize(() => this.loadingSubject.next(false))
    ).subscribe(persons => {
      console.log(persons);
      this.personsSubject.next(persons);
    });

  }

  connect(collectionViewer: CollectionViewer): Observable<Person[]> {
    console.log('Connecting data source');
    return this.personsSubject.asObservable();
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.personsSubject.complete();
    this.loadingSubject.complete();
  }

}
