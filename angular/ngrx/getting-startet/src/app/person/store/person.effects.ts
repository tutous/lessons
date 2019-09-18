import { Injectable } from '@angular/core';
import { Actions, Effect, ofType } from '@ngrx/effects';
import { PersonService } from './person.service';
import { LoadPersonsAction, LoadPersonsSuccessAction, LoadPersonsFailureAction } from './person.actions';
import * as PersonActions from "./person.actions";
import { map, mergeMap, catchError } from 'rxjs/operators';
import { of } from 'rxjs';

@Injectable()
export class PersonEffects {

    constructor(
        private actions$: Actions,
        private personService: PersonService
    ) { }

    @Effect()
    loadPersons$ = this.actions$
        .pipe(
            ofType<LoadPersonsAction>(PersonActions.LOAD_PERSONS),
            mergeMap(() => this.personService.getPersons()
                .pipe(
                    map(persons => {
                        return new LoadPersonsSuccessAction(persons)
                    }),
                    catchError(error => of(new LoadPersonsFailureAction(error)))
                )
            ),
        )

}