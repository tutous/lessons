// import from module.ts
import * as foo1 from 'foo';
// das geht auch
import foo2 = require('foo');

console.log(foo1.bar);
console.log(foo2.bar);
