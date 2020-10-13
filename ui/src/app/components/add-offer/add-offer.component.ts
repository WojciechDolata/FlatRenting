import {Component, OnInit} from '@angular/core';
import {OfferService} from "../../services/offer.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Offer} from "../../models/models";
import {HttpResponse} from "@angular/common/http";

@Component({
  selector: 'app-add-offer',
  templateUrl: './add-offer.component.html',
  styleUrls: ['./add-offer.component.css']
})
export class AddOfferComponent implements OnInit {
  newOfferForm: FormGroup;
  fileToUpload: File = null;

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
    var offer: Offer = {
      id: null,
      creationTimestamp: null,
      title: formValue.title,
      location: formValue.location,
      roomCount: formValue.roomCount,
      description: formValue.description,
      size: formValue.size,
      //TODO: fetch from session (cookies or sth)
      owner: null,
      photos: []
    };

    this.offerService.addNewOffer(offer)
      .subscribe(offer => {
        console.log("Sent new offer: " + offer);
        this.upload(offer.id);
      });
  }

  handleFileInput(event) {
    this.fileToUpload = event.target.files.item(0);
  }

  upload(id: number) {
    this.offerService.addPhotoToOffer(id, this.fileToUpload).subscribe(event => {
        if (event instanceof HttpResponse) {
          alert('File Successfully Uploaded');
        }
        this.fileToUpload = undefined;
      }
    );
  }
}
