import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {Offer} from "../models/models";
import {HttpClient, HttpEvent, HttpParams, HttpRequest} from "@angular/common/http";
import {map} from "rxjs/operators";
import {environment} from "../../environments/environment";
import {AuthService} from "./auth.service";

@Injectable({
  providedIn: 'root'
})
export class OfferService {


  private baseUrl = environment.SERVER_URL;
  private offerUrl = this.baseUrl + "offer";
  private preferencesUrl = this.baseUrl + "preferences";

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) { }

  private fixDate(offer: Offer): Offer {
    offer.creationTimestamp = new Date(
      offer.creationTimestamp[0],
      offer.creationTimestamp[1]-1,
      offer.creationTimestamp[2],
      offer.creationTimestamp[3],
      offer.creationTimestamp[4],
      offer.creationTimestamp[5]
    )
    return offer;
  }

  getOffers(pageNumber: number): Observable<Offer[]> {
    return this.http.get<Offer[]>(this.offerUrl + "/all/" + pageNumber).pipe(
      map( offerList => offerList.map(this.fixDate)
      )
    );
  }

  getOffer(id: number): Observable<Offer> {
    return this.http.get<Offer>(this.offerUrl + "/getBy/" + id).pipe(
      map( offer => this.fixDate(offer))
    );
  }

  getOffersBy(searchQuery: string,
              descriptionCheck: string,
              roomCount: string,
              location: string,
              size: string,
              orderBy: string,
              page: number): Observable<Offer[]> {
    let params = new HttpParams();


    if(searchQuery != "") {
      params = params.append("searchQuery", searchQuery);

      if(descriptionCheck != "") {
        params = params.append("descriptionCheck", String(descriptionCheck));
      }
    }

    if(location != "Pick location...") {
      params = params.append("location", location);
    }

    if(roomCount != "Room count...") {
      params = params.append("roomCount", roomCount);
    }

    if(size != "Size...") {
      params = params.append("size", size);
    }

    params = params.append("orderBy", orderBy);
    params = params.append("page", page.toString());

    return this.http.get<Offer[]>(
      this.offerUrl + "/allBy",{params: params}
    ).pipe(
      map( offerList => offerList.map(this.fixDate)
      )
    );
  }

  getAllForUser(nick: string): Observable<Offer[]> {
    return this.http.get<Offer[]>(this.offerUrl + "/allFor/" + nick, {headers: this.authService.getHeaders()});
  }

  addNewOffer(offer: Offer, ownerNick: string): Observable<Offer> {
    return this.http.post<Offer>(this.offerUrl + "/add/" + ownerNick, offer, {headers: this.authService.getHeaders()}).pipe(
      map( offer => this.fixDate(offer))
    );
  }

  addPhotoToOffer(id: number, file: File): Observable<HttpEvent<{}>> {
    const data: FormData = new FormData();
    data.append('file', file);
    const newRequest = new HttpRequest('POST', this.offerUrl + "/addPhoto/" + id, data, {
      headers: this.authService.getHeaders(),
      reportProgress: true,
      responseType: 'text'
    });

    return this.http.request(newRequest);
  }

  getPreferredOffers(): Observable<Offer[]> {
    const data = new FormData();
    data.append('nick', this.authService.getNick());
    return this.http.post<Offer[]>(
      this.preferencesUrl + "/getOffers",
      data,
      {headers: this.authService.getHeaders()}
    )
  }

  deleteOffer(id): Observable<any> {
    return this.http.get(this.offerUrl + "/delete/" + id, {headers: this.authService.getHeaders()});
  }
}
