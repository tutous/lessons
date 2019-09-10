import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Car } from '../domain/car';
import { Observable } from 'rxjs';

@Injectable()
export class CarService {

    constructor(private http: HttpClient) { }

    getCarsMedium(): Observable<Car[]> {
        return this.http.get<Car[]>('assets/showcase/data/cars-medium.json');
    }

    getCarsHuge(): Observable<Car[]> {
        return this.http.get<Car[]>('assets/showcase/data/cars-huge.json');
    }
}