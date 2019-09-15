import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
  <nav style="padding: 10px">
    <span><a [routerLink]="['/simple']">simple table</a></span>
  </nav>
  <router-outlet></router-outlet>
  `,
  styles: []
})
export class AppComponent {
  title = 'modules';
}
