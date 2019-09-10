// Import a variable or a type using import
import { someVarA, SomeTypeA, someVarB, SomeTypeB, someVarDifferentName } from './export01';

console.log(someVarA);
let someTypeA: SomeTypeA = { foo: 'halloA' };
console.log(someTypeA);

console.log(someVarB);
let someTypeB: SomeTypeB = { foo: 'halloB' };
console.log(someTypeB);

console.log(someVarDifferentName);

// Re-Exporting only some items from another module
export { someVarA as someVarWithRename }