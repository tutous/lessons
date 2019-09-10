import { Person } from '../domain/Person';
import { Observable } from 'rxjs';
export declare interface PersonService {

    add: (person: Person) => void;
    getAll: () => Observable<Person>
    filter: (filterValue: string) => Observable<Person>;
    greetAll: () => void;

}