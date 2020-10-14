import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-offer-not-found',
  templateUrl: './offer-not-found.component.html',
  styleUrls: ['./offer-not-found.component.css']
})
export class OfferNotFoundComponent implements OnInit {

  @Input("id") id: number;

  constructor() { }

  ngOnInit(): void {
  }

}
