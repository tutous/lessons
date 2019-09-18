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


    rowGroupMetadata: any;

    constructor(private carService: CarService) { }

    ngOnInit() {
        this.carService.getCarsHuge().subscribe(vehicles => {
            this.vehicles = vehicles;
            this.updateRowGroupMetaData();
        });
    }

    onSort() {
        this.updateRowGroupMetaData();
    }

    updateRowGroupMetaData() {
        this.rowGroupMetadata = {};
        if (this.vehicles) {
            for (let i = 0; i < this.vehicles.length; i++) {
                let rowData = this.vehicles[i];
                let brand = rowData.brand;
                if (i == 0) {
                    this.rowGroupMetadata[brand] = { index: 0, size: 1 };
                }
                else {
                    let previousRowData = this.vehicles[i - 1];
                    let previousRowGroup = previousRowData.brand;
                    if (brand === previousRowGroup)
                        this.rowGroupMetadata[brand].size++;
                    else
                        this.rowGroupMetadata[brand] = { index: i, size: 1 };
                }
            }
        }
        console.log(this.rowGroupMetadata);
    }

}