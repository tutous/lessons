import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-person',
  template: `
    <person-create></person-create>
    <person-read></person-read>
  `,
  styles: []
})
export class PersonComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
