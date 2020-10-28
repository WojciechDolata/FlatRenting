import {Component, OnInit} from '@angular/core';
import {OfferService} from "../../../services/offer.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Offer} from "../../../models/models";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {Router} from "@angular/router";
import {AuthService} from "../../../services/auth.service";

@Component({
  selector: 'app-add-offer',
  templateUrl: './add-offer.component.html',
  styleUrls: ['./add-offer.component.css']
})
export class AddOfferComponent implements OnInit {
  newOfferForm: FormGroup;
  filesToUpload: File[] = null;
  loading = false;

  constructor(
    private offerService: OfferService,
    private authService: AuthService,
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
      flatSize: '',
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
      size: formValue.flatSize,
      price: formValue.price,
      owner: null,
      photos: [],
      visitCount: 0
    };

    this.offerService.addNewOffer(offer, this.authService.getNick())
      .subscribe(offer => {
        if(this.filesToUpload) {
          this.upload(offer.id);
        } else {
          this.initFormGroup();
          this.router.navigateByUrl("/offer-detail/" + offer.id);
        }
      });

  }

  handleFileInput(event) {
    this.filesToUpload = event.target.files;
  }

  upload(id: number) {
    this.loading = true;
    let loadedPhotosCount = 0;
    let totalPhotosCount = this.filesToUpload.length;
    for(let i = 0; i < this.filesToUpload.length; i++) {
      this.offerService.addPhotoToOffer(id, this.filesToUpload[i]).subscribe(event => {
          if (event instanceof HttpErrorResponse) {
            alert('Error while uploading files');
          } else if(event instanceof HttpResponse) {
            loadedPhotosCount++;
            if(loadedPhotosCount == totalPhotosCount) {
              this.loading = false;
              this.initFormGroup();
              this.router.navigateByUrl("/offer-detail/" + id);
            }
          }

        }
      );
    }

    this.filesToUpload = null;
  }

  isLogged() {
    return this.authService.authenticated;
  }
}
