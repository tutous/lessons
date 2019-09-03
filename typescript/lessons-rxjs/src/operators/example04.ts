export { };
import { of, from, Observable, merge, zip } from 'rxjs';
import { mergeAll, concatAll, filter, first } from 'rxjs/operators';

/**
 * Filtering Operators
 */

let obsv01: Observable<number> = of(1, 2, 3, 4, 2);
// filter
obsv01.pipe(filter(x => x > 2)).subscribe(x => console.log('filter(x>2): ' + x));
// first
obsv01.pipe(first(x => x > 2)).subscribe(x => console.log('first(x>2): ' + x));