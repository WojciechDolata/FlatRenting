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
    this.id = +this.route.snapshot.paramMap.get('id');
    this.offerService.getOffer(this.id)
      .subscribe(offer => {
        this.offer = offer;
      });
  }

  isNew(): boolean {

    const weekAgoDate = new Date;
    weekAgoDate.setHours(weekAgoDate.getHours() - 24*3);
    return this.offer.creationTimestamp >= weekAgoDate;
  }

  howManyDaysAgo(): string {
    let dayCount = Math.round(Math.abs(Date.now() - this.offer.creationTimestamp.getTime())/(1000*60*60*24));
    if (dayCount < 1) {
      return "in the last 24 hours.";
    } else {
      return dayCount + " days ago."
    }
  }
}
