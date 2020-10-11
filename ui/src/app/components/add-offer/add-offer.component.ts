import {Component, OnInit} from '@angular/core';
import {OfferService} from "../../services/offer.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Offer} from "../../models/models";

@Component({
  selector: 'app-add-offer',
  templateUrl: './add-offer.component.html',
  styleUrls: ['./add-offer.component.css']
})
export class AddOfferComponent implements OnInit {
  newOfferForm: FormGroup;

  constructor(
    private offerService: OfferService,
    private formBuilder: FormBuilder
  ) {
    this.newOfferForm = this.formBuilder.group({
      title: '',
      location: '',
      roomCount: '',
      size: '',
      description: ''
    });
  }

  ngOnInit(): void {
  }

  onSubmit(formValue) {
    console.log(formValue);
    var offer: Offer = {
      id: null,
      creationTimestamp: null,
      title: formValue.title,
      location: formValue.location,
      roomCount: formValue.roomCount,
      description: formValue.description,
      //TODO: fetch from session (cookies or sth)
      owner: null,
      photos: []
    };

    this.offerService.addNewOffer(offer)
      .subscribe(offer => console.log("Sent new offer: " + offer));
  }
}
