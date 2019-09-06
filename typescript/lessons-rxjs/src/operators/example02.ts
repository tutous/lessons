export { };
import { of, from, Observable } from 'rxjs';
import { map, filter, first, flatMap, concatAll, mergeAll, switchAll, exhaust, concatMap, switchMap } from 'rxjs/operators';

/**
 * Transformation Operators
 */
interface Request {
    method: string;
    response: string[];
}

let requests: Request[] = [
    { method: 'GET', response: ['1', '2'] },
    { method: 'GET', response: ['3', '4', '5'] }
]

let observable: Observable<Request> = from(requests);
observable.pipe(
    map(req => req.response) // Array(2) ["1", "2"] and Array(3) ["3", "4", "5"]
).subscribe(req => console.log('map ' + req));
/** concatAll -> subscribes to each inner Observable as it arrives,
 * then emits each value as it arrives
 */
observable.pipe(
    map(req => req.response),
    concatAll() /** 1,2,3,4,5,6 */
).subscribe(req => console.log('concatAll ' + req));
/**
 * switchAll -> subscribes to the first inner Observable when it arrives, 
 * and emits each value as it arrives, but when the next inner Observable arrives,
 * unsubscribes to the previous one , and subscribes to the new one
 */
observable.pipe(
    map(req => req.response),
    switchAll() /** 1,2,3,4,5,6 */
).subscribe(req => console.log('switchAll ' + req));
/**
 * exhaust -> subscribes to the first inner Observable when it arrives,
 * and emits each value as it arrives, discarding all newly arriving inner Observables
 * until that first one completes, then waits for the next inner Observable
 */
observable.pipe(
    map(req => req.response),
    exhaust() /** 1,2,3,4,5,6 */
).subscribe(req => console.log('exhaust ' + req));


// concatMap
observable.pipe(
    map(req => req.response),
    concatMap(array => array.filter(v => v != '1' && v != '6')) /** 2,3,4,5 */
).subscribe(req => console.log('concatMap + filter ' + req));
// switchMap
observable.pipe(
    map(req => req.response),
    switchMap(array => array.filter(v => v != '1' && v != '6')) /** 2,3,4,5 */
).subscribe(req => console.log('switchMap + filter ' + req));
// exhaustMap
observable.pipe(
    map(req => req.response),
    switchMap(array => array.filter(v => v != '1' && v != '6')) /** 2,3,4,5 */
).subscribe(req => console.log('exhaustMap + filter ' + req));
// flatMap
observable.pipe(
    map(req => req.response),
    flatMap(array => array.filter(v => v != '1' && v != '6')) /** 2,3,4,5 */
).subscribe(req => console.log('flatMap + filter ' + req));