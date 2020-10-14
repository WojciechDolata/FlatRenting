import {Component, OnInit} from '@angular/core';
import {Offer} from "../../models/models";
import {OfferService} from "../../services/offer.service";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-offer-search',
  templateUrl: './offer-search.component.html',
  styleUrls: ['./offer-search.component.css']
})
export class OfferSearchComponent implements OnInit {
  offers: Offer[];
  searchForm: FormGroup;
  order = "newest";

  constructor(
    private offerService: OfferService,
    private formBuilder: FormBuilder
  ) { }

  ngOnInit(): void {
    this.getOffers();
    this.initFormGroup();
  }

  private initFormGroup() {
    this.searchForm = this.formBuilder.group({
      searchString: '',
      descriptionCheckbox: '',
      roomCount: 'Room count...',
      size: 'Size...',
      orderBy: '1'
    });
  }

  filterOffers(formValue) {
    this.offerService.getOffersBy(
      formValue.searchString,
      formValue.descriptionCheckbox,
      formValue.roomCount,
      formValue.size,
      formValue.orderBy
    ).subscribe(data => this.offers = data);
  }

  getOffers() {
    this.offerService.getOffers().subscribe(
      data => this.offers = data.reverse()
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
