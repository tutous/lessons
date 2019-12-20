export { };

interface Column {
    field: string;
    header: string;
}

interface Car {
    vin: string;
    year: number;
    brand: string;
    color: string;
}

let cols: Column[] = [
    { field: 'year', header: 'Year' },
    { field: 'brand', header: 'Brand' },
    { field: 'color', header: 'Color' },
    { field: 'vin', header: 'Vin' }
];

let cars: Car[] = [
    {
        brand: "BMW",
        vin: "d00250a3",
        year: 1978,
        color: "Blue"
    },
    {
        vin: "f3c3909d",
        brand: "Renault",
        year: 2003,
        color: "Green"
    }
]

cars.forEach(car => {
    cols.forEach(col => {
        console.log(`${col.header} ${car[col.field]}`);
    });
});

// example 2

let dynaData:any = {};

dynaData['prop1'] = { index: 0, size: 0 };
dynaData['prop1'].index = 1;

dynaData['prop2'] = { index: 0, size: 0 };
dynaData['prop2'].index = 1;

console.log(dynaData);

// example 3 ...variable

let array01:number[]=[1,2,3];
let array02:number[]=[...array01,4,5,6];
console.log(array02);

interface Person {
  name: string;
  foreName: string;
  city: string;
}

let uwe: Person = {
  name: "Sluga",
  foreName: "Uwe",
  city: "Wob"
};

console.log(uwe);
let anton: Person = { ...uwe, foreName: "Anton" };
console.log(anton);