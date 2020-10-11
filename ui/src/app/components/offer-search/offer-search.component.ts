import {Component, OnInit} from '@angular/core';
import {Offer} from "../../models/models";
import {OfferService} from "../../services/offer.service";

@Component({
  selector: 'app-offer-search',
  templateUrl: './offer-search.component.html',
  styleUrls: ['./offer-search.component.css']
})
export class OfferSearchComponent implements OnInit {
  offers: Offer[];

  constructor(
    private offerService: OfferService
  ) { }

  ngOnInit(): void {
    this.getOffers();
  }

  getOffers() {
    this.offerService.getOffers().subscribe(
      data => this.offers = data
    );
  }
}
