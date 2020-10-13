import {Component, OnInit} from '@angular/core';
import {OfferService} from "../../services/offer.service";
import {Offer} from "../../models/models";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-offer-details',
  templateUrl: './offer-details.component.html',
  styleUrls: ['./offer-details.component.css']
})
export class OfferDetailsComponent implements OnInit {

  id: number;

  offer: Offer;

  constructor(
    private offerService: OfferService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.fetchOffer();
  }

  fetchOffer() {
    const id = +this.route.snapshot.paramMap.get('id');
    this.offerService.getOffer(id)
      .subscribe(offer => this.offer = offer);
  }

  isNew(): boolean {
    const weekAgoDate = new Date;
    weekAgoDate.setHours(weekAgoDate.getHours() - 24*7);

    return this.offer.creationTimestamp >= weekAgoDate;
  }
}
