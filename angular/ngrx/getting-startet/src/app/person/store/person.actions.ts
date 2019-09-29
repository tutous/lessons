import { createAction, props } from "@ngrx/store";
import { Person } from "./person";

export const loadPersons = createAction<string>("[Person] load");

export const loadPersonsSuccess = createAction<string, { persons: Person[] }>(
  "[Person] load success",
  props<{ persons: Person[] }>()
);

export const loadPersonsFailure = createAction<string, { error: Error }>(
  "[Person] load failure",
  props<{ error: Error }>()
);

export const addPerson = createAction<string, { person: Person }>(
  "[Person] add",
  props<{ person: Person }>()
);
export const removePerson = createAction("[Person] remove");
