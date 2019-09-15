import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
  <nav>
    <div style="padding: 10px"><a [routerLink]="['/binding']">lesson binding</a></div>
    <div style="padding: 10px"><a [routerLink]="['/directive/structural']">lesson structural directive</a></div>
  </nav>
  <router-outlet></router-outlet>
  `,
  styles: []
})
export class AppComponent {
  title = 'basic';
}
