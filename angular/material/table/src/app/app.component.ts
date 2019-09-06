import { Person } from './domain/person';
import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { PersonsService } from './persons.service';
import { PersonsDataSource } from './persons.datasource';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { fromEvent } from 'rxjs';
import { debounceTime, distinctUntilChanged, tap, merge } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, AfterViewInit {

  title = 'Material Table';

  person: Person;
  dataSource: PersonsDataSource;
  displayedColumns = ['Name', 'Vorname', 'Stadt'];

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  constructor(private personsService: PersonsService) {

  }

  ngOnInit() {
    this.dataSource = new PersonsDataSource(this.personsService);
    this.dataSource.loadAllPersons(0, 3);
  }

  onRowClicked(person: Person) {
    console.log(person);
  }

  ngAfterViewInit() {
    this.paginator.page.pipe(
      tap(() => this.loadLessonsPage())
    ).subscribe();
  }

  loadLessonsPage() {
    this.dataSource.loadAllPersons(
      this.paginator.pageIndex,
      this.paginator.pageSize);
  }
}
