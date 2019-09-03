export {};
import { of, from } from "rxjs";
import { map, filter, first, flatMap } from "rxjs/operators";
/**
 * Creation Operators: of, from, interval, ...
 * Pipeable Operators:map, filter, first ...
 * 
 */
of(1, 2, 3, 4)
  .pipe(
    map(x => x + 1),
    filter(x => x > 3),
    first(x => x == 4)
  )
  .subscribe(v => console.log(`value: ${v}`));

from([1, 2, 3, 4])
  .pipe(
    map(x => x + 1),
    filter(x => x > 3),
    first(x => x == 4)
  )
  .subscribe(v => console.log(`value: ${v}`));

let firstX: number;
from([1, 2, 3, 4])
  .pipe(
    map(x => x + 1),
    filter(x => x > 3),
    first(x => x == 4)
  )
  .subscribe(v => (firstX = v));
console.log(firstX);

of([1, 2, 3, 4], [5, 6, 7, 8])
  .pipe(
    flatMap(x => x),
    filter(x => x > 3 && x < 6),
    first(x => x == 4)
  )
  .subscribe(v => (firstX = v));
console.log(firstX);
