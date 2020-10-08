import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {Offer} from "../models/models";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class OfferService {

  private offerUrl = "http://localhost:8080/offer/all";

  constructor(
    private http: HttpClient
  ) { }

  getOffers(): Observable<Offer[]> {
    return this.http.get<Offer[]>(this.offerUrl);
  }
}
