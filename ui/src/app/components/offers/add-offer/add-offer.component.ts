import {Component, OnInit} from '@angular/core';
import {OfferService} from "../../../services/offer.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Marker, Offer} from "../../../models/models";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {Router} from "@angular/router";
import {AuthService} from "../../../services/auth.service";
import {cities} from "../../../../environments/cities";
import {GoogleMapsService} from "../../../services/google-maps.service";
import LatLng = google.maps.LatLng;
import computeDistanceBetween = google.maps.geometry.spherical.computeDistanceBetween;

@Component({
  selector: 'app-add-offer',
  templateUrl: './add-offer.component.html',
  styleUrls: ['./add-offer.component.css']
})
export class AddOfferComponent implements OnInit {
  readonly MAX_DISTANCE_FROM_CITY_CENTER = 25000; //in meters

  newOfferForm: FormGroup;
  filesToUpload: File[] = null;
  loading = false;
  location: string;
  mapCenter: LatLng;
  marker: Marker;

  constructor(
    private offerService: OfferService,
    private authService: AuthService,
    private mapsService: GoogleMapsService,
    private formBuilder: FormBuilder,
    private router: Router
  ) {
    this.initFormGroup();
  }

  private initFormGroup() {
    this.newOfferForm = this.formBuilder.group({
      title: '',
      location: 'Warszawa',
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
      locationX: this.marker ? this.marker.x : null,
      locationY: this.marker ? this.marker.y : null,
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

  updateMapLocation(location) {
    this.marker = null;
    this.mapsService.getLocationByCityName(location).subscribe(
      data => this.mapCenter = data
    );
  }

  public onMapClick(event) {
    this.marker = {
      x: event.latLng.lat(),
      y: event.latLng.lng(),
      title: 'Selected location'
    }

    if(this.getMarkerDistanceFromCenter() > this.MAX_DISTANCE_FROM_CITY_CENTER) {
      alert("Select location closer to the city center (max 25km)!");
      this.marker = null;
    }
  }

  getMarkerDistanceFromCenter(): number {
    // @ts-ignore
    return computeDistanceBetween(new LatLng(this.mapCenter.lat, this.mapCenter.lng), new LatLng(this.marker.x, this.marker.y));
  }

  getCities() {
    return cities;
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
