import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
    <person-create></person-create>
    <person-read></person-read>
  `,
  styles: []
})
export class AppComponent {
  title = 'getting-startet';
}
