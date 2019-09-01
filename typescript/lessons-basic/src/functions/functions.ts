export {};
// Named function
function adda(x, y) {
  return x + y;
}
console.log(adda(1, 1));

// Anonymous function
let addb = function(x, y) {
  return x + y;
};
console.log(addb(1, 1));

// typing
function addc(x: number, y: number): number {
  return x + y;
}

let addd = function(x: number, y: number): number {
  return x + y;
};
let adde: (x: number, y: number) => number = function(
  x: number,
  y: number
): number {
  return x + y;
};

// optinal parameter
let func01: (name: string, time?: number) => void = function(
  name: string,
  time?: number
): void {
  console.log(name + " " + time);
};
func01("Uwe", 10);
func01("Uwe");

// default parameter
let func02: (name: string, time?: number) => void = function(
  name: string,
  time: number = 10
): void {
  console.log(name + " " + time);
};
func02("Uwe");

// Overloads
function doSomething(x: number): void;
function doSomething(x: string,y?:number): void;
function doSomething(x,y?): void {
  console.log('doSomething ' + x);
}
doSomething('Uwe');
doSomething(10);
