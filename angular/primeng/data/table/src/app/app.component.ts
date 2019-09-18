import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
  <nav style="padding: 10px">
    <span style="padding: 10px"><a [routerLink]="['/simple']">simple table</a></span>
    <span style="padding: 10px"><a [routerLink]="['/colgrp']">colgrp table</a></span>
    <span style="padding: 10px"><a [routerLink]="['/rowgrp']">rowgrp table</a></span>
  </nav>
  <router-outlet></router-outlet>
  `,
  styles: []
})
export class AppComponent {
  title = 'modules';
}
