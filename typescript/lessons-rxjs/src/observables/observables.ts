export { }
import { Observable, from, OperatorFunction, MonoTypeOperatorFunction, interval, of, Subscription } from 'rxjs';
import { Person } from './person';
import { Logger } from './logger';
import { map, tap } from 'rxjs/operators';

/**
 * observable -> beobachtbar
 * subscriber -> Abonnent, Besteller, Teilnehmer
 * subscribe  -> abonnieren
 */

let observableNumbers = new Observable<number>(subscriber => {
    try {
        subscriber.next(1);
        subscriber.next(2);
        subscriber.next(3);
        //  setTimeout(() => { asynchronously out
        subscriber.next(4);
        subscriber.complete();
        //  }, 1000);
    } catch (error) {
        subscriber.error(error);
    }

});
let subscription:Subscription = observableNumbers.subscribe(
    // pushed values
    value => console.log(`Number ${value}`),
    // raised an error
    error => console.log('error'),
    // or finished without error
    () => console.log('completed')
);
subscription.unsubscribe();

// example 02
let persons: Person[] = [
    { name: 'Sluga', foreName: 'Uwe' },
    { name: 'Sluga', foreName: 'Sabine' }];
let observablePersons = from(persons); // Observable by from
Logger.new<Person>().log(observablePersons);

// example 03 Person to string
// upercase pipe
let upperOrLowerCase: (upper?: boolean) => MonoTypeOperatorFunction<string> =
    function (upper: boolean = true): MonoTypeOperatorFunction<string> {
        let operatorFunctionUC: MonoTypeOperatorFunction<string> = inputObservable => {
            // return new output observable
            return new Observable<string>(outputSubscriber => {
                inputObservable.subscribe(inputValue => {
                    if (upper) {
                        outputSubscriber.next(inputValue.toUpperCase());
                    }
                    else {
                        outputSubscriber.next(inputValue.toLowerCase());
                    }
                });
            });
        };
        return operatorFunctionUC;
    };

// only the name
let observableNames = observablePersons.pipe(
    map(p => p.foreName),
    upperOrLowerCase(false));
Logger.new<string>().log(observableNames);

// example 04 interval
let observableNumbersByInterval = new Observable<number>(subscriber => {
    let index = 0;
    setInterval(() => { subscriber.next(index++) }, 1000);
});
Logger.new<number>().log(observableNumbersByInterval);

interval(1000)
