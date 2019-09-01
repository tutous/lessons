export {};
abstract class Animal {
  private _name: string;
  private _description: string = "";
  public constructor(theName: string) {
    this._name = theName;
  }
  public move(distanceInMeters: number = 0) {
    console.log(`${this._name} moved ${distanceInMeters}m.`);
  }
  public set description(description: string) {
    this._description = description;
  }

  public get description(): string {
    return this._description;
  }
  abstract makeSound(): void;
}

class Snake extends Animal {
  constructor(name: string) {
    super(name);
  }
  move(distanceInMeters = 5) {
    console.log("Slithering...");
    super.move(distanceInMeters);
  }
  makeSound() {
    console.log("psss");
  }
}

class Horse extends Animal {
  constructor(name: string) {
    super(name);
  }
  move(distanceInMeters = 45) {
    console.log("Galloping...");
    super.move(distanceInMeters);
  }
  makeSound() {
    console.log("üüüh");
  }
}

let sam = new Snake("Sammy the Python");
let tom: Animal = new Horse("Tommy the Palomino");

sam.move();
sam.makeSound();
tom.move(34);
tom.makeSound();
