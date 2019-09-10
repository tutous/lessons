import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-buyer-update',
  template: `
    <br>  
    <nav>
    <span style="padding: 10px"><a routerLink="/buyer/update/europe">Buyer update Europe.</a></span>
    <span style="padding: 10px"><a routerLink="/buyer/update/america">Buyer update America.</a></span>
    </nav>
    <router-outlet></router-outlet>`,
  styleUrls: []
})
export class BuyerUpdateComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
