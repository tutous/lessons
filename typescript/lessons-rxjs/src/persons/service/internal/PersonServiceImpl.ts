import { Person } from '../../domain/Person';
import { Observable, of } from 'rxjs';
import { PersonRepository } from '../../repository/PersonRepository';
import { PersonService } from '../PersonService';
import { Repository } from '../../repository/Repository';

export class PersonServiceImpl implements PersonService {

    private personRepository: Repository<Person>;

    constructor(personRepository: Repository<Person>) {
        this.personRepository = personRepository;
    }

    public add(person: Person) {
        this.personRepository.add(person);
    }

    public getAll(): Observable<Person> {
        let result: Observable<Person> = new Observable<Person>(
            subscriber => {
                this.personRepository.getAll().forEach(p => {
                    subscriber.next(p);
                });
                // subscriber.error("error");
                subscriber.complete();
            }
        );
        return result;
    }

    public filter(filterValue: string): Observable<Person> {
        let result: Observable<Person> = new Observable<Person>(subscriber => {
            this.personRepository.getAll().filter(p => this.contains(p, filterValue)).forEach(p =>
                subscriber.next(p)
            );
        });
        return result;
    }

    public greetAll() {
        this.personRepository.getAll().forEach(p => this.greeter(p));
    }

    private contains(p: Person, filterValue: string): boolean {
        return p.foreName.indexOf(filterValue) >= 0 || p.name.indexOf(filterValue) >= 0;
    }

    private greeter(person: Person): void {
        console.log('Hello,' + person.foreName + ' ' + person.name);
    }
}