import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {Offer} from "../models/models";
import {HttpClient, HttpEvent, HttpRequest} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class OfferService {

  private offerUrl = "http://localhost:8080/offer";

  constructor(
    private http: HttpClient
  ) { }

  getOffers(): Observable<Offer[]> {
    return this.http.get<Offer[]>(this.offerUrl + "/all");
  }

  getOffer(id: number): Observable<Offer> {
    return this.http.get<Offer>(this.offerUrl + "/" + id);
  }

  addNewOffer(offer: Offer): Observable<Offer> {
    return this.http.post<Offer>(this.offerUrl + "/add", offer);
  }

  addPhotoToOffer(id: number, file: File): Observable<HttpEvent<{}>> {
    const data: FormData = new FormData();
    data.append('file', file);
    console.log(data);
    const newRequest = new HttpRequest('POST', this.offerUrl + "/" + id + "/addPhoto", data, {
      reportProgress: true,
      responseType: 'text'
    });

    return this.http.request(newRequest);
  }
}
