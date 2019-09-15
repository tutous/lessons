import { PersonService } from './PersonService';
import { PersonServiceImpl } from './internal/PersonServiceImpl';
import { PersonRepository } from '../repository/PersonRepository';
import { Repository } from '../repository/Repository';
import { Person } from '../domain/Person';
export class PersonServiceFactory {

    private static personRepository: Repository<Person>;

    static get(): PersonService {
        if (!PersonServiceFactory.personRepository) {
            PersonServiceFactory.personRepository = new PersonRepository();
        }
        return new PersonServiceImpl(PersonServiceFactory.personRepository);
    }

}