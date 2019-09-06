import { PersonsService } from './persons.service';
import { Person } from './domain/person';
import { CollectionViewer, DataSource } from '@angular/cdk/collections';
import { Observable, BehaviorSubject, of } from 'rxjs';
import { catchError, finalize, toArray } from 'rxjs/operators';

export class PersonsDataSource implements DataSource<Person> {

  private personsSubject = new BehaviorSubject<Person[]>([{ foreName: '-', name: '-', city: '-' }]);

  private loadingSubject = new BehaviorSubject<boolean>(false);
  private notLoadingSubject = new BehaviorSubject<boolean>(true);

  public loading$ = this.loadingSubject.asObservable();
  public notLoading$ = this.notLoadingSubject.asObservable();

  public recordSize$ = new BehaviorSubject<number>(0);

  constructor(private coursesService: PersonsService) {

  }

  loadAllPersons(nameFilter: string, foreNameFilter: string, cityFilter: string,
    sortColumn: string, sortDirection: string,
    pageIndex: number, pageSize: number) {

    this.loadingSubject.next(true);
    this.notLoadingSubject.next(false);

    this.coursesService.findAllPersons(nameFilter, foreNameFilter, cityFilter, sortColumn, sortDirection, pageIndex, pageSize)
      .pipe(
        toArray(),
        catchError(() => of([])),
        finalize(() => {
          this.recordSize$.next(5);
          this.notLoadingSubject.next(true);
          this.loadingSubject.next(false);
        })
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
