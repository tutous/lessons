import * as Actions from "./person.actions";
import { PersonState } from './person.state';

const initialState: PersonState = {
  persons: [],
  loading: false,
  error: undefined
}

let personReducer = function personReducer(state: PersonState = initialState, action: Actions.PersonActions): PersonState {
  switch (action.type) {
    case Actions.LOAD_PERSONS:
      return { ...state, loading: true };
    case Actions.LOAD_PERSONS_SUCCESS:
      return { ...state, persons: action.persons, loading: false };
    case Actions.LOAD_PERSONS_FAILURE:
      return { ...state, error: action.error, loading: false };
    case Actions.ADD_PERSON:
      return { persons: [...state.persons, action.person], loading: false, error: undefined };
    default:
      return state;
  }
}

export { personReducer as PersonReducer };
