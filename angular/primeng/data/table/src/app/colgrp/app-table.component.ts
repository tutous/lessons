import { Component, OnInit } from '@angular/core';
import { Vehicle } from '../domain/vehicle';
import { CarService } from '../service/car.service';
import { Observable } from 'rxjs';
import { Column } from '../domain/column';

@Component({
    selector: 'app-table',
    templateUrl: './app-table.component.html'
})
export class AppTableComponent implements OnInit {

    vehicles: Vehicle[] = new Array<Vehicle>();

    cols: Column[];

    constructor(private carService: CarService) { }

    ngOnInit() {
        this.carService.getCarsMedium().subscribe(vehicles => this.vehicles = vehicles);

        this.cols = [
            { field: 'vin', header: 'Vin' },
            { field: 'year', header: 'Year' },
            { field: 'brand', header: 'Brand' },
            { field: 'color', header: 'Color' }
        ];
    }

}