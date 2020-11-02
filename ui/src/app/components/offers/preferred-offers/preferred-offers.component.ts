import {Component, OnInit} from '@angular/core';
import {OfferService} from "../../../services/offer.service";
import {Offer} from "../../../models/models";
import {UserService} from "../../../services/user.service";
import {AuthService} from "../../../services/auth.service";

@Component({
  selector: 'app-preferred-offers',
  templateUrl: './preferred-offers.component.html',
  styleUrls: ['./preferred-offers.component.css']
})
export class PreferredOffersComponent implements OnInit {

  offers: Offer[];
  loading = true;
  hasPreferences: boolean;

  constructor(
    private offerService: OfferService,
    private userService: UserService,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.fetchData();
  }

  fetchData() {
    this.loading = true;
    this.offerService.getPreferredOffers().subscribe(
      data => this.offers = data,
      () => null,
      () => this.loading = false);

    this.userService.hasPreferences().subscribe(
      data => {
        this.hasPreferences = data;
      }
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

  isLogged() {
    return this.authService.authenticated;
  }

}
