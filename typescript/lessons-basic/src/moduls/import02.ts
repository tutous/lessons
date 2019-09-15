// Import a variable or a type using import with renaming
import { someVarA as someVarAA } from './export01';
// Import everything from a module into a name with 'export01'
import * as export01 from './export01';
// import from default with rename
import MySomeClass from './export02';
// import of Re-Exporting only some items from another module
import { someVarWithRename } from './import01';

console.log(someVarAA);
let someTypeA: export01.SomeTypeA = { foo: 'halloA' };
console.log(someTypeA);

console.log(export01.someVarB);
let someTypeB: export01.SomeTypeB = { foo: 'halloB' };
console.log(someTypeB);

console.log(new MySomeClass());

console.log(someVarWithRename);