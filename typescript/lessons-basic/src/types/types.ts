export {};

// example 01: default typing by init
console.log("# example 01");

let var01 = 10;
// error: let var01 = 'hallo';
let typeOfvar01 = typeof var01;
console.log(`I am a ${typeOfvar01}`);

// example 02: default typing without init
console.log("# example 02");
let var02;
var02 = 10;
var02 = "hallo";

// excample 03: const
console.log("# example 03");
let var03: number = 10;
var03 = 20;
const const03 = 10;
// error: const03 = 10; could not be changed any mores
console.log(var03 + const03);

// example 04: multy typing
console.log("# example 04");
let var04: number | string = 10;
var04 = 10;
var04 = "hallo";
// error: var04=boolean;

// example 05 any
console.log("# example 05");
let var05: any = "123";
console.log(typeof var05);
var05 = 10;
console.log(typeof var05);
var05 = true;
console.log(typeof var05);
var05 = "all is possible";

// example 06 undefined or null
console.log("# example 06");
let var06: any = undefined;
console.log(typeof var06);
var06 = null;
console.log(typeof var06);
var06 = "hallo";
console.log(typeof var06);

// example 07 type
console.log("example 07");

type Person = { name: string; city: string; age?: number };
let var07: Person = { name: "Uwe", city: "Wob", age: 10 };
console.log(var07);
var07 = { name: "Sabine", city: "Wob" };
console.log(var07);

// example 08 interface
console.log("# example 08");

enum Gender {
  MALE = "male",
  FEMAL = "female"
}
interface Employee {
  id: number;
  fullName: string;
  gender: Gender;
}
let employeea:Employee = { id: 1, fullName: "Uwe Sluga", gender: Gender.MALE };
console.log(employeea);
// override by type assertion
let employeeb:any = {};
console.log(typeof employeeb);
employeeb = {} as Employee;
employeeb.id = 2;
employeeb.fullName = "Sabine Sluga";
//employeeb.gender = Gender.FEMAL;
console.log(employeeb);

// example 09
console.log("# example 09");
let var091: any = 10;
let var092: any = "10";
console.log(var091 == var092); // true
console.log(var091 === var092); // false

// example 10 overload
interface Logger {
  (values: string[]): string;
  (values: number[]): string;
}

function log(values: string[]): string;
function log(values: number[]): string;
function log(values: any[]): string {
  let result: string = "log: ";
  for (const value of values) {
    result += " " + value;
  }
  console.log(result);
  return result;
}

const logger: Logger = log;

logger(["my", "message"]);
logger([1, 2]);

// Freshness
function logName(something: { name: string }) {
    console.log(something.name);
}

var person = { name: 'matt', job: 'being awesome' };
var animal = { name: 'cow', diet: 'vegan, but has milk of own species' };

logName(person); // okay
logName(animal); // okay