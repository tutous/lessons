import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Person } from './person';
import { PersonService } from './person.service';


@Component({
  selector: 'app-root',
  template: `

  <table>

    <tr *ngFor="let person of persons | async" (click)="show(person.id)">
     <td>{{person.foreName}}</td>
     <td>{{person.name}}</td>
    </tr>

  </table>

  <ul *ngIf="person">
    <li>{{person.foreName}}</li>
    <li>{{person.name}}</li>
    <li>{{person.city}}</li>
  </ul>
    
  `,
  styles: [`
    td {
      width: 300px;
      height: 30px;
    }
  `]
})
export class AppComponent implements OnInit {
  title = 'webapi';
  persons: Observable<Person[]>;
  person: Person;

  constructor(private personService: PersonService) {

  }

  ngOnInit() {
    this.persons = this.personService.getPersons();
  }

  show(id: number) {
    this.personService.getPerson(id).subscribe(person => this.person = person);
  }
}
