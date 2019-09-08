import { Person, Status } from "./person";
export const PERSONS: Person[] = [
  {
    foreName: "Uwe",
    name: "Sluga",
    city: "Wolfsburg",
    visibleCity: true,
    status: Status.VALID
  },
  {
    foreName: "Peter",
    name: "Kleinschmidt",
    city: "Hamburg",
    visibleCity: true,
    status: Status.PENDING
  },
  {
    foreName: "Leon",
    name: "Stark",
    city: "Berlin",
    visibleCity: false,
    status: Status.ERROR
  },
  {
    foreName: "Anton",
    name: "Meier",
    city: "Braunschweig",
    visibleCity: true,
    status: Status.UNKNOWN
  }
];
