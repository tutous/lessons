import { Component, OnInit } from '@angular/core';
import { Car } from '../domain/car';
import { CarService } from '../service/car.service';
import { Observable } from 'rxjs';

@Component({
    selector: 'app-table',
    templateUrl: './app-table.component.html'
})
export class AppTableComponent implements OnInit {

    cars: Car[] = new Array<Car>();

    cols: any[];

    constructor(private carService: CarService) { }

    ngOnInit() {
        this.carService.getCarsMedium().subscribe(cars => this.cars = cars);

        this.cols = [
            { field: 'vin', header: 'Vin' },
            { field: 'year', header: 'Year' },
            { field: 'brand', header: 'Brand' },
            { field: 'color', header: 'Color' }
        ];
    }
}