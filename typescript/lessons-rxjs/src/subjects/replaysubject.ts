export { }
import { ReplaySubject } from 'rxjs';

// example 01

// buffer 3 values for new subscribers
const subject01 = new ReplaySubject(3);

subject01.subscribe({
    next: (v) => console.log(`observer01A: ${v}`)
});

subject01.next(1);
subject01.next(2);
subject01.next(3);
subject01.next(4);

subject01.subscribe({
    next: (v) => console.log(`observer01B: ${v}`)
});

subject01.next(5);

// example 02

const subject02 = new ReplaySubject(100, 500 /* windowTime */);
 
subject02.subscribe({
  next: (v) => console.log(`observer02A: ${v}`)
});
 
let i = 1;
setInterval(() => subject02.next(i++), 200);
 
setTimeout(() => {
    subject02.subscribe({
    next: (v) => console.log(`observer02B: ${v}`)
  });
}, 1000);