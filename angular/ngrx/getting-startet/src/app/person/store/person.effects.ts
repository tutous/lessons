import { Injectable } from "@angular/core";
import { Actions, Effect, ofType } from "@ngrx/effects";
import { PersonService } from "./person.service";
import {
  loadPersons,
  loadPersonsSuccess,
  loadPersonsFailure
} from "./person.actions";
import { map, mergeMap, catchError } from "rxjs/operators";
import { of } from "rxjs";

@Injectable()
export class PersonEffects {
  constructor(
    private actions$: Actions,
    private personService: PersonService
  ) {}

  @Effect()
  loadPersons$ = this.actions$.pipe(
    ofType(loadPersons.type),
    mergeMap(() =>
      this.personService.getPersons().pipe(
        map(persons => {
          return loadPersonsSuccess({ persons: persons });
        }),
        catchError(error => of(loadPersonsFailure({ error: error })))
      )
    )
  );
}
