export { };
import { of, from, Observable, merge, zip } from 'rxjs';
import { mergeAll, concatAll } from 'rxjs/operators';

/**
 *  Join Creation Operators
 */

let obsv01: Observable<number> = new Observable<number>(
    subscriber => {
        subscriber.next(1);
        setTimeout(() => {
            subscriber.next(2);
        }, 100);
    }
);
let obsv02: Observable<number> = of(3, 4);
/** 1,3,4,2 */
merge(obsv01, obsv02).subscribe(x => console.log('merge ' + x));
/** 1,3,2,4 */
zip(obsv01, obsv02).pipe(concatAll()).subscribe(x => console.log('zip ' + x));
