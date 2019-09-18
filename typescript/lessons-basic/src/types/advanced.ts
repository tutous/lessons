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
    { field: 'vin', header: 'Vin' },
    { field: 'year', header: 'Year' },
    { field: 'brand', header: 'Brand' },
    { field: 'color', header: 'Color' }
];

let cars: Car[] = [
    {
        vin: "d00250a3",
        brand: "BMW",
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
