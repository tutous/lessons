export { }

import { ToString, ToStringProp } from "./tostring";

@ToString()
class Greeter {
    @ToStringProp(true)
    private greeting: string;
    @ToStringProp(false)
    private prefix: string = "Hello";
    constructor(message: string) {
        this.greeting = message;
    }
    greet() {
        return `${this.prefix} ${this.greeting}`;
    }
}

let greeter: Greeter = new Greeter("Uwe");
console.log(greeter.toString());
console.log(greeter.greet());
