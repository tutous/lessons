import { createReducer, on } from "@ngrx/store";
import { AppState } from "./app.state";
import { initialPersonState } from "./person/store/person.reducer";

const initialAppState: AppState = {
  personState: initialPersonState
};

const appReducer = createReducer(initialAppState);

export const APP_REDUCER_KEY = "appReducerKey";
export { appReducer as AppReducer };
