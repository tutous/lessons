import * as PersonActions from "./person.actions";
import { Person } from "./person";

export function reducer(state: Person[] = [], action: PersonActions.Actions) {
  switch (action.type) {
    case PersonActions.ADD_PERSON:
      return [...state, action.payload];
    default:
      return state;
  }
}
