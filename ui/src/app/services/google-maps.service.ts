import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";
import {Observable} from "rxjs";
import LatLng = google.maps.LatLng;

@Injectable({
  providedIn: 'root'
})
export class GoogleMapsService {

  private mapUrl = "https://maps.googleapis.com/maps/api/geocode/json?";
  private key = "AIzaSyDjKaD2Cl8SMzLCK3UCH7swyI2gd6LjRNo";

  constructor(private http: HttpClient) { }

  getLocationByCityName(cityName: string): Observable<LatLng> {
    return this.http.get<LatLng>(this.mapUrl + "address=" + (cityName ? cityName : 'warszawa') + "&key=" + this.key)
      .pipe(
        map(data => data.results[0].geometry.location as LatLng));
  }

}
