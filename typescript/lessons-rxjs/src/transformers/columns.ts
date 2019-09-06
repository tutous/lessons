export { };
import { Observable, OperatorFunction } from 'rxjs';
import { finalize } from "rxjs/operators";

interface Person {
    foreName: string;
    name: string;
}
interface Column {
    index: number;
}
interface ForeNameColumn extends Column {
    foreName: string;
}
interface NameColumn extends Column {
    name: string;
}

interface PersonColumns {
    foreNames: ForeNameColumn[];
    names: NameColumn[];
}

let persons: Person[] = [
    { foreName: 'Uwe', name: 'Sluga' },
    { foreName: 'Max', name: 'Mustermann' }
]

let toColumns: () => OperatorFunction<Person, Column[]> =
    function (): OperatorFunction<Person, Column[]> {
        let operatorFunction: OperatorFunction<Person, Column[]> = inputObservable => {
            // return new output observable
            return new Observable<Column[]>(columnsSubscriber => {
                inputObservable.subscribe(person => {
                    let colForeName: ForeNameColumn = { index: 0, foreName: person.foreName };
                    let colName: NameColumn = { index: 1, name: person.name };
                    let columns: Column[] = [colForeName, colName];
                    columnsSubscriber.next(columns);
                });
            });
        };
        return operatorFunction;
    };

let collectColumns: (personColumns: PersonColumns) => OperatorFunction<Column[], Column[]> =
    function (personColumns: PersonColumns): OperatorFunction<Column[], Column[]> {
        let operatorFunction: OperatorFunction<Column[], Column[]> = inputObservable => {
            return new Observable<Column[]>(columnsSubscriber => {
                inputObservable.subscribe(columns => {
                    columns.forEach(c => {
                        if (c.index == 0) {
                            personColumns.foreNames.push(c as ForeNameColumn);
                        }
                        else if (c.index == 1) {
                            personColumns.names.push(c as NameColumn);
                        }
                    });
                    columnsSubscriber.next(columns);
                });
            });
        };
        return operatorFunction;
    }

let obsrv01: Observable<Person> = new Observable(subscriber => {
    persons.forEach(p => subscriber.next(p));
    subscriber.complete();
});

const columns: PersonColumns = { foreNames: new Array(), names: new Array() };
obsrv01.pipe(
    toColumns(),
    collectColumns(columns),
    finalize(() => console.log('unsubscribe')
    )
).subscribe().unsubscribe();

columns.foreNames.forEach(c => console.log(`${c.index} ${c.foreName}`));
columns.names.forEach(c => console.log(`${c.index} ${c.name}`));
