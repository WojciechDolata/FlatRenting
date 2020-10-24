import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../services/auth.service";
import {OfferService} from "../../../services/offer.service";
import {MessageService} from "../../../services/message.service";
import {Conversation, Offer} from "../../../models/models";
import {ActivatedRoute} from "@angular/router";
import DateUtils from "../../../utils/date-utils";

@Component({
  selector: 'app-offer-conversations',
  templateUrl: './offer-conversations.component.html',
  styleUrls: ['./offer-conversations.component.css']
})
export class OfferConversationsComponent implements OnInit {
  offer: Offer;
  conversations: Conversation[];

  constructor(private authService: AuthService,
              private offerService: OfferService,
              private messageService: MessageService,
              private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.fetchData();
  }

  fetchData() {
    this.offerService.getOffer(+this.route.snapshot.paramMap.get('id'))
      .subscribe(offer => {
        this.offer = offer;
        this.messageService.getAllConversationsForOffers([offer.id])
          .subscribe(
            data => {
              this.conversations = data[0];
            }
        )
      });
  }

  getLoggedUser() {
    return this.authService.getNick();
  }

  isAuthorized(): boolean {
    if(this.offer) {
      return this.offer.owner.nick === this.getLoggedUser();
    }
    return false;
  }

  formatDate(date) {
    return DateUtils.formatDate(date);
  }

  setAsCurrentConversation(conversation: Conversation) {
    this.authService.currentConversation = conversation;
  }

}
