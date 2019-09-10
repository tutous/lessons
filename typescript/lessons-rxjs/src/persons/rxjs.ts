import { Person } from './domain/Person';
import { PersonService } from './service/PersonService';
import { PersonServiceFactory } from './service/PersonServiceFactory';
import { OperatorFunction } from 'rxjs';

export class RxjsExample {

    run() {

        /** first example */

        let service: PersonService = PersonServiceFactory.get();
        service.add({id:10, name: "Musterman", foreName: 'Thomas' });
        service.greetAll();

        /** second example: Observable subscriber */

        console.log('start get all');
        PersonServiceFactory.get().getAll().subscribe(

            /** person log */
            p => {
                let foreName: string = p.foreName;
                let name: string = p.name;
                console.log(` Person: ${foreName} ${name}`);
            },

            /** error log */
            error => console.log(' finished with error: ${error}'),

            /** finished lod */
            () => console.log('finished get all'));

        /** third example: Observable pipe */

        let logFunction: OperatorFunction<Person, Person> = (persons) => {
            persons.forEach(p => console.log('Person: ' + p.foreName + ' ' + p.name));
            return persons;
        }

        PersonServiceFactory.get().filter('An').pipe(logFunction);
    }

}