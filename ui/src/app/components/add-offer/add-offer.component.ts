import {Component, OnInit} from '@angular/core';
import {OfferService} from "../../services/offer.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Offer} from "../../models/models";
import {HttpErrorResponse} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-add-offer',
  templateUrl: './add-offer.component.html',
  styleUrls: ['./add-offer.component.css']
})
export class AddOfferComponent implements OnInit {
  newOfferForm: FormGroup;
  filesToUpload: File[] = null;

  constructor(
    private offerService: OfferService,
    private formBuilder: FormBuilder,
    private router: Router
  ) {
    this.initFormGroup();
  }

  private initFormGroup() {
    this.newOfferForm = this.formBuilder.group({
      title: '',
      location: '',
      roomCount: '1',
      size: '<25',
      price: '',
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
      price: formValue.price,
      //TODO: fetch from session (cookies or sth)
      owner: null,
      photos: []
    };

    this.offerService.addNewOffer(offer)
      .subscribe(offer => {
        console.log("Sent new offer: " + offer);
        this.upload(offer.id);
        this.initFormGroup();
        this.router.navigateByUrl("/offer-detail/" + offer.id);
      });

  }

  handleFileInput(event) {
    this.filesToUpload = event.target.files;
  }

  upload(id: number) {
    for(let i = 0; i < this.filesToUpload.length; i++) {
      this.offerService.addPhotoToOffer(id, this.filesToUpload[i]).subscribe(event => {
          if (event instanceof HttpErrorResponse) {
            alert('Error while uploading files');
          }
        }
      );
    }

    this.filesToUpload = null;
  }
}
