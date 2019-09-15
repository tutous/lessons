import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-buyer-create',
  template: `
    <br>
    <nav>
      <span style="padding: 10px"><a routerLink="/buyer/create/europe">Buyer create Europe.</a></span>
      <span style="padding: 10px"><a routerLink="/buyer/create/america">Buyer create America.</a></span>
    </nav>
    <router-outlet></router-outlet>`,
  styleUrls: []
})
export class BuyerCreateComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
