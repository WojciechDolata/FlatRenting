import {Component, OnInit} from '@angular/core';
import {OfferService} from "../../../services/offer.service";
import {Offer} from "../../../models/models";
import {ActivatedRoute} from "@angular/router";
import {AuthService} from "../../../services/auth.service";
import {MessageService} from "../../../services/message.service";
import LatLng = google.maps.LatLng;

@Component({
  selector: 'app-offer-details',
  templateUrl: './offer-details.component.html',
  styleUrls: ['./offer-details.component.css']
})
export class OfferDetailsComponent implements OnInit {

  id: number;

  offer: Offer;

  shouldCreateConversation: boolean

  constructor(
    private offerService: OfferService,
    private messageService: MessageService,
    private route: ActivatedRoute,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.fetchOffer();
  }

  getOfferPosition() {
    if (this.offer != null) {
      return new LatLng(this.offer.locationX, this.offer.locationY);
    }
    return null;
  }

  fetchOffer() {
    this.id = +this.route.snapshot.paramMap.get('id');
    this.offerService.getOffer(this.id)
      .subscribe(offer => {
        this.offer = offer;
        this.setShouldCreateConversation();
      });
  }

  isNew(): boolean {

    const weekAgoDate = new Date;
    weekAgoDate.setHours(weekAgoDate.getHours() - 24*3);
    return this.offer.creationTimestamp >= weekAgoDate;
  }

  howManyDaysAgo(): string {
    let dayCount = Math.round(Math.abs(Date.now() - this.offer.creationTimestamp.getTime())/(1000*60*60*24));
    if (dayCount < 1) {
      return "in the last 24 hours.";
    } else {
      return dayCount + " days ago."
    }
  }

  setShouldCreateConversation() {
    if(this.offer.owner.nick === this.authService.getNick()) {
      this.shouldCreateConversation = false;
    } else {
      this.messageService.getConversationsByNick().subscribe(
        conversations => {
          if(conversations.length === 0) {
            this.shouldCreateConversation = true;
          } else {
            this.shouldCreateConversation = !conversations
              .map(conversation => conversation.offer.id === this.offer.id)
              .reduce((a,b) => a || b );
          }
        }
      );
    }
  }
}
