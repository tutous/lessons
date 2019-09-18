import { Person } from "./person";

export declare interface PersonState {
  persons: Person[],
  loading: boolean,
  error: Error
}
