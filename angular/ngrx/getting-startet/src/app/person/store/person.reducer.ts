import { createReducer, on } from "@ngrx/store";
import { PersonState } from "./person.state";
import {
  loadPersons,
  loadPersonsSuccess,
  loadPersonsFailure,
  addPerson,
  removePerson
} from "./person.actions";

export const initialPersonState: PersonState = {
  persons: [],
  loading: false,
  error: undefined
};

const personReducer = createReducer(
  initialPersonState,
  on(loadPersons, state => {
    return { ...state, loading: true };
  }),
  on(loadPersonsSuccess, (state, { persons }) => {
    return { ...state, persons: persons, loading: false };
  }),
  on(loadPersonsFailure, (state, { error }) => {
    return { ...state, error: error, loading: false };
  }),
  on(addPerson, (state, { person }) => {
    state.persons.push(person);
    return { ...state };
  })
);

export const PERSON_FEATURE_KEY = "personFeatureKey";

export { personReducer as PersonReducer };
