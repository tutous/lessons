// Exporting with the keyword export
export let someVarA = 123;
export type SomeTypeA = {
  foo: string;
};

// Exporting in a dedicated export statement
let someVarB = 123;
type SomeTypeB = {
  foo: string;
};
export {
  someVarB,
  SomeTypeB
};

// Exporting in a dedicated export statement with renaming
let someVarC = 123;
export { someVarC as someVarDifferentName };