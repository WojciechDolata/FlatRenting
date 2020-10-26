import {Component, OnInit} from '@angular/core';
import {OfferService} from "../../../services/offer.service";
import {Offer} from "../../../models/models";
import {UserService} from "../../../services/user.service";

@Component({
  selector: 'app-preferred-offers',
  templateUrl: './preferred-offers.component.html',
  styleUrls: ['./preferred-offers.component.css']
})
export class PreferredOffersComponent implements OnInit {

  offers: Offer[];
  hasPreferences: boolean;

  constructor(
    private offerService: OfferService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.fetchData();
  }

  fetchData() {
    this.offerService.getPreferredOffers().subscribe(
      data => this.offers = data
    );

    this.userService.hasPreferences().subscribe(
      data => this.hasPreferences = data
    );
  }

  splitOffersToThreeInRow(): Offer[][] {
    let splitOffers = [];


    if(this.offers) {
      for(let i = 0; i < this.offers.length; i = i + 3) {
        splitOffers.push(this.offers.slice(i, i+3));
      }
    }

    return splitOffers;
  }

}
