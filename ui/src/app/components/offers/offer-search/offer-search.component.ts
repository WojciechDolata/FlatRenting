import {Component, HostListener, OnInit} from '@angular/core';
import {Offer} from "../../../models/models";
import {OfferService} from "../../../services/offer.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {environment} from "../../../../environments/environment";

@Component({
  selector: 'app-offer-search',
  templateUrl: './offer-search.component.html',
  styleUrls: ['./offer-search.component.css']
})
export class OfferSearchComponent implements OnInit {
  offers: Offer[] = [];
  searchForm: FormGroup;
  order = "newest";
  pageNumber = 0;
  private canFetchMore = true;
  private hasAlreadyFetchedAll = false;
  private areFiltersApplied = false;
  loading = false;

  constructor(
    private offerService: OfferService,
    private formBuilder: FormBuilder
  ) { }

  ngOnInit(): void {
    this.pageNumber = 0;
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
    this.pageNumber = 0;
    this.offers = [];
    this.areFiltersApplied = true;
    this.hasAlreadyFetchedAll = false;
    this.getFilteredOffers();
  }

  getFilteredOffers() {
    this.offerService.getOffersBy(
        this.searchForm.value.searchString,
        this.searchForm.value.descriptionCheckbox,
        this.searchForm.value.roomCount,
        this.searchForm.value.size,
        this.searchForm.value.orderBy,
        this.pageNumber
      ).subscribe(
      data => {
        data.forEach(item => this.offers.push(item));
        if(data.length != environment.OFFER_PAGE_SIZE) {
          this.hasAlreadyFetchedAll = true;
        }
        this.pageNumber++;
        this.canFetchMore = true;
        this.loading = false;
      }
    );
  }

  getOffers() {
    this.offerService.getOffers(this.pageNumber).subscribe(
      data => {
        data.forEach(item => this.offers.push(item));
        if(data.length != environment.OFFER_PAGE_SIZE) {
          this.hasAlreadyFetchedAll = true;
        }
        this.pageNumber++;
        this.canFetchMore = true;
        this.loading = false;
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

  private loadMoreOffers() {
    this.loading = true;
    if(this.areFiltersApplied) {
      this.getFilteredOffers();
    } else {
      this.getOffers();
    }
  }

  @HostListener('window:scroll', ['$event']) // for window scroll events
  onScroll(event) {
    let screenHeight = window.screen.availHeight;
    let scrollPosition = window.scrollY;
    if(scrollPosition > screenHeight - 200 && this.canFetchMore && !this.hasAlreadyFetchedAll) {
      this.canFetchMore = false;
      this.loadMoreOffers();
    }
  }
}
