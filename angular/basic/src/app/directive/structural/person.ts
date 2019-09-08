export interface Person {
  name: string;
  foreName: string;
  city: string;
  visibleCity: boolean;
  status: Status;
}
export enum Status {
  VALID = "valid",
  ERROR = "error",
  PENDING = "pending",
  UNKNOWN = ''
}
