export { }

interface Overloaded {
    (foo: string): string
    (foo: number): number
    (foo: boolean): string
}

// example implementation
function stringOrNumber(foo: number): number;
function stringOrNumber(foo: string): string;
function stringOrNumber(foo: boolean): string;

function stringOrNumber(foo: any): any {
    if (typeof foo === 'number') {
        return foo * foo;
    } else if (typeof foo === 'string') {
        return `hello ${foo}`;
    }
    else if (typeof foo === 'boolean') {
        return `is ${foo}`;
    }
}
const overloaded: Overloaded = stringOrNumber;

// example usage
const str = overloaded('Uwe'); // type of `str` is inferred as `string`
const num = overloaded(123); // type of `num` is inferred as `number`
const boolean = overloaded(true);

console.log(str);
console.log(num);
console.log(boolean);
