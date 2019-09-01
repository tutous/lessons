export {};

// execc property
interface Shape {
  readonly id: number;
  width: number;
  hight: number;
  // add other properties
  [property: string]: any;
}

let shape: Shape = { id: 1, width: 10, hight: 10, color: "red", line: 1 };
console.log(shape);
// error shape.id= 2;

// function types
interface SearchFunction {
  // parameter source and caseSensitive return boolean
  (source: string, caseSensitive: boolean): boolean;
}
let search: SearchFunction = function(source, caseSensitive) {
  let content: string = "Hallo Uwe!";
  if (!caseSensitive) {
    content = content.toUpperCase();
    source = source.toUpperCase();
  }
  if (content.indexOf(source) >= 0) {
    return true;
  }
  return false;
};
console.log(search("uwe", false));

// index types
interface CollectionSearchFunction {
  [index: number]: SearchFunction;
}
let searchs: CollectionSearchFunction;
searchs = [
  search,
  function(source, caseSensitive) {
    return false;
  }
];
console.log(searchs);

// class type
enum Species {
  MAMMAL,
  BIRD,
  AMPHIBIAN
}
interface AnimalIF {
  id: number;
  species: Species;
  describe(): string;
}
interface DogIF extends AnimalIF {
  name: string;
}
class Dog implements DogIF {
  name: string;
  id: number;
  species: Species;
  constructor(id: number, name: string, species: Species) {
    this.id = id;
    this.name = name;
    this.species = species;
  }
  describe(): string {
    return `I am a ${this.name}`;
  }
}
let labrador: Dog = new Dog(1, "labrador", Species.MAMMAL);
console.log(labrador.describe());

// clock example
interface ClockConstructor {
  new (hour: number, minute: number): ClockInterface;
}
interface ClockInterface {
  tick(): void;
}

function createClock(
  clockConstructor: ClockConstructor,
  hour: number,
  minute: number
): ClockInterface {
  return new clockConstructor(hour, minute);
}

class DigitalClock implements ClockInterface {
  constructor(h: number, m: number) {}
  tick() {
    console.log("beep beep");
  }
}
class AnalogClock implements ClockInterface {
  constructor(h: number, m: number) {}
  tick() {
    console.log("tick tock");
  }
}

let digital = createClock(DigitalClock, 12, 17);
let analog = createClock(AnalogClock, 7, 32);

// hybrid types
interface Counter {
  (start: number): Counter;
  interval: number;
  reset(): void;
}

function getCounter(): Counter {
  let counter = function(start: number) {
    interval: 123;
  } as Counter;
  counter.reset = function() {};
  return counter;
}

let c = getCounter();
c(10);
c.reset();
c.interval = 5.0;
console.log(c);
