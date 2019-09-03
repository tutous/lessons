import { Observable } from 'rxjs';
export class Logger<T> {

    static new<T>() {
        return new Logger<T>();
    }

    log: (observable: Observable<T>) => void = function (observable: Observable<T>): void {
        observable.subscribe(value => {
            console.log(value as T);
        })
    };
}