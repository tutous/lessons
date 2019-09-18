import { Action } from "@ngrx/store";
import { Person } from "./person";

export const ADD_PERSON = "ADD_PERSON";
export const REMOVE_PERSON = "REMOVE_PERSON";

export class AddPerson implements Action {
  readonly type = ADD_PERSON;
  constructor(public payload: Person) {}
}
export class RemovePerson implements Action {
  readonly type = REMOVE_PERSON;
  constructor(public payload: number) {}
}
export type Actions = AddPerson | RemovePerson;
