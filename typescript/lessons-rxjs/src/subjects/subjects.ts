export { }

import { Subject, Observable, from, ConnectableObservable } from 'rxjs';
import { multicast } from 'rxjs/operators';

// example 01
const subject01 = new Subject<number>();
const observable01: Observable<number> = subject01;

observable01.subscribe({
    next: (v) => console.log(`observer01A: ${v}`)
});
observable01.subscribe({
    next: (v) => console.log(`observer01B: ${v}`)
});

subject01.next(1);
subject01.next(2);

// example 02
const subject02 = new Subject<number>();
subject02.subscribe({
    next: (v) => console.log(`observer02A: ${v}`)
});
subject02.subscribe({
    next: (v) => console.log(`observer02B: ${v}`)
});

const source02 = from([1, 2, 3]);

// You can subscribe providing a Subject
source02.subscribe(value => {
    subject02.next(value);
});
// is the same
source02.subscribe(subject02); 

// example 03
const source = from([1, 2, 3]);
const target = new Subject();
const observable:ConnectableObservable<number> = source.pipe(multicast(target)) as ConnectableObservable<number>;
 
// These are, under the hood, `subject.subscribe({...})`:
observable.subscribe({
  next: (v) => console.log(`observer03A: ${v}`)
});
observable.subscribe({
  next: (v) => console.log(`observer03B: ${v}`)
});
 
// This is, under the hood, `source.subscribe(subject)`:
observable.connect();