import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Vehicle } from '../domain/vehicle';
import { Observable } from 'rxjs';

@Injectable()
export class CarService {

    constructor(private http: HttpClient) { }

    getCarsMedium(): Observable<Vehicle[]> {
        return this.http.get<Vehicle[]>('assets/showcase/data/cars-medium.json');
    }

    getCarsHuge(): Observable<Vehicle[]> {
        return this.http.get<Vehicle[]>('assets/showcase/data/cars-huge.json');
    }
}