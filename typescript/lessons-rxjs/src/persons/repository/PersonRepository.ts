import { Person } from '../domain/Person';
import { PERSONS } from './Persons';
import { Repository } from './Repository';
export class PersonRepository implements Repository<Person> {

    private persons: Person[];

    constructor() {
        this.persons = PERSONS;
    }

    getAll(): Person[] {
        return this.persons;
    }

    add(person: Person) {
        this.persons.push(person);
    }

}