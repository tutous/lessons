import { Action } from '@ngrx/store';
import { Person } from "./person";

export const ADD_PERSON = "ADD_PERSON";
export const REMOVE_PERSON = "REMOVE_PERSON";

export const LOAD_PERSONS = "LOAD_PERSON";
export const LOAD_PERSONS_SUCCESS = "LOAD_PERSON_SUCCESS";
export const LOAD_PERSONS_FAILURE = "LOAD_PERSON_FAILURE";

export class LoadPersonsAction implements Action {
  readonly type = LOAD_PERSONS;
}

export class LoadPersonsSuccessAction implements Action {
  readonly type = LOAD_PERSONS_SUCCESS;
  constructor(public persons: Person[]) { }
}

export class LoadPersonsFailureAction implements Action {
  readonly type = LOAD_PERSONS_FAILURE;
  constructor(public error: Error) { }
}

export class AddPersonAction implements Action {
  readonly type = ADD_PERSON;
  constructor(public person: Person) { }
}

export class RemovePersonAction implements Action {
  readonly type = REMOVE_PERSON;
  constructor(public index: number) { }
}

export type PersonActions = AddPersonAction | RemovePersonAction | LoadPersonsAction | LoadPersonsSuccessAction | LoadPersonsFailureAction;
