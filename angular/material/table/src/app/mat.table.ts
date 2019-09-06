import { Person } from './domain/person';
import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { PersonsService } from './persons.service';
import { PersonsDataSource } from './persons.datasource';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { merge, fromEvent } from 'rxjs';
import { debounceTime, distinctUntilChanged, tap } from 'rxjs/operators';

@Component({
  selector: 'app-mat-table',
  templateUrl: './mat.table.html',
  styleUrls: ['./mat.table.css']
})
export class MatTable implements OnInit, AfterViewInit {

  title = 'Material Table';

  person: Person;
  dataSource: PersonsDataSource;
  displayedColumns = ['Name', 'Vorname', 'Stadt'];

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('inputForname', { static: true }) inputForname: ElementRef;
  @ViewChild('inputName', { static: true }) inputName: ElementRef;
  @ViewChild('inputCity', { static: true }) inputCity: ElementRef;

  constructor(private personsService: PersonsService) {

  }

  ngOnInit() {
    this.dataSource = new PersonsDataSource(this.personsService);
    this.dataSource.loadAllPersons('', '', '', '', '', 0, 2);
  }

  onRowClicked(person: Person) {
    console.log(person);
  }

  ngAfterViewInit() {
    // search by name
    fromEvent(this.inputName.nativeElement, 'keyup')
      .pipe(
        debounceTime(150),
        distinctUntilChanged(),
        tap(() => {
          this.paginator.pageIndex = 0;
          this.loadLessonsPage();
        })
      ).subscribe();
    // search by forename
    fromEvent(this.inputForname.nativeElement, 'keyup')
      .pipe(
        debounceTime(150),
        distinctUntilChanged(),
        tap(() => {
          this.paginator.pageIndex = 0;
          this.loadLessonsPage();
        })
      ).subscribe();
    // search by city
    fromEvent(this.inputCity.nativeElement, 'keyup')
      .pipe(
        debounceTime(150),
        distinctUntilChanged(),
        tap(() => {
          this.paginator.pageIndex = 0;
          this.loadLessonsPage();
        })
      ).subscribe();
    // reset the paginator to 0 after sorting
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    // merge events and load new page
    merge(this.sort.sortChange, this.paginator.page).pipe(
      tap(() => this.loadLessonsPage())
    ).subscribe();
  }

  loadLessonsPage() {
    this.dataSource.loadAllPersons(
      this.inputName.nativeElement.value,
      this.inputForname.nativeElement.value,
      this.inputCity.nativeElement.value,
      this.sort.active,
      this.sort.direction,
      this.paginator.pageIndex,
      this.paginator.pageSize);
  }
}
