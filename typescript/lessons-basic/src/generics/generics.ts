export {};
interface Logger {}

function getTypeof<T>(arg: T): string {
  return typeof arg;
}
console.log(getTypeof("hallo"));

let logger: Logger = {};
console.log(getTypeof(logger));

let typeofFunc: <U>(arg: U) => string = function<T>(arg: T): string {
  return typeof arg;
};

// interface
interface GenericIdentity<T> {
  (arg: T): string;
}
let genericIdentityFunct: GenericIdentity<number> = function(
  arg: number
): string {
  return typeof arg;
};
console.log(genericIdentityFunct(10));
