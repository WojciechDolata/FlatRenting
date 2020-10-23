import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {Offer} from "../models/models";
import {HttpClient, HttpEvent, HttpParams, HttpRequest} from "@angular/common/http";
import {map} from "rxjs/operators";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class OfferService {


  private baseUrl = environment.serverUrl;
  private offerUrl = this.baseUrl + "offer";

  constructor(
    private http: HttpClient
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

  getOffers(): Observable<Offer[]> {
    return this.http.get<Offer[]>(this.offerUrl + "/all").pipe(
      map( offerList => offerList.map(this.fixDate)
      )
    );
  }

  getOffer(id: number): Observable<Offer> {
    return this.http.get<Offer>(this.offerUrl + "/" + id).pipe(
      map( offer => this.fixDate(offer))
    );
  }

  getOffersBy(searchQuery: string,
              descriptionCheck: string,
              roomCount: string,
              size: string,
              orderBy: string): Observable<Offer[]> {
    let params = new HttpParams();


    if(searchQuery != "") {
      params = params.append("searchQuery", searchQuery);

      if(descriptionCheck != "") {
        params = params.append("descriptionCheck", String(descriptionCheck));
      }
    }

    if(roomCount != "Room count...") {
      params = params.append("roomCount", roomCount);
    }

    if(size != "Size...") {
      params = params.append("size", size);
    }

    params = params.append("orderBy", orderBy);

    return this.http.get<Offer[]>(
      this.offerUrl + "/allBy",{params: params}
    ).pipe(
      map( offerList => offerList.map(this.fixDate)
      )
    );
  }

  getAllForUser(nick: string): Observable<Offer[]> {
    return this.http.get<Offer[]>(this.offerUrl + "/" + nick +"/all");
  }

  addNewOffer(offer: Offer, ownerNick: string): Observable<Offer> {
    return this.http.post<Offer>(this.offerUrl + "/add/" + ownerNick, offer).pipe(
      map( offer => this.fixDate(offer))
    );
  }

  addPhotoToOffer(id: number, file: File): Observable<HttpEvent<{}>> {
    const data: FormData = new FormData();
    data.append('file', file);
    const newRequest = new HttpRequest('POST', this.offerUrl + "/" + id + "/addPhoto", data, {
      reportProgress: true,
      responseType: 'text'
    });

    return this.http.request(newRequest);
  }
}
