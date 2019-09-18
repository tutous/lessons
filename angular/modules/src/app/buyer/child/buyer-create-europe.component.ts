import { Component, OnInit, AfterViewInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-buyer-create-europe',
  template: `
    <p style="padding: 10px" >Create buyer {{europeLocation}} Europe.</p>
    <button style="padding: 10px" title="back" (click)="goBack()">Zurück</button>
  `,
  styleUrls: []
})
export class BuyerCreateEuropeComponent implements OnInit {

  europeLocation: string;

  constructor(private route: ActivatedRoute, private router: Router, private location: Location) {
    router.events.subscribe((val) => {
      this.europeLocation = this.route.snapshot.paramMap.get('location');
    });
  }

  ngOnInit() {
  }

  goBack(): void {
    this.location.back();
  }

}
