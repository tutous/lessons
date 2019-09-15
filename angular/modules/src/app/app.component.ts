import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
    <nav>
    <span style="padding: 10px"><a [routerLink]="['/buyer/create']">Buyer create.</a></span>
    <span style="padding: 10px"><a [routerLink]="['/buyer/update']">Buyer update.</a></span>
    </nav>
    <router-outlet></router-outlet>
  `,
  styles: []
})
export class AppComponent {
  title = 'modules';
}
