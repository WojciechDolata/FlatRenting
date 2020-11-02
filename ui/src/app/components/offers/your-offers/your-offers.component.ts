import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../services/auth.service";
import {Conversation, Offer} from "../../../models/models";
import {MessageService} from "../../../services/message.service";
import {OfferService} from "../../../services/offer.service";

@Component({
  selector: 'app-your-offers',
  templateUrl: './your-offers.component.html',
  styleUrls: ['./your-offers.component.css']
})
export class YourOffersComponent implements OnInit {
  offers: Offer[];
  conversationLists: Conversation[][] = null;
  offerListMap = new Map<Offer, Conversation[]>();
  loading = true;

  constructor(private authService: AuthService,
              private offerService: OfferService,
              private messageService: MessageService
  ) { }

  ngOnInit(): void {
    this.fetchData();
  }

  fetchData() {
    this.loading = true;
    this.offerService.getAllForUser(this.authService.getNick()).subscribe(
      data => {
        this.offers = data;
        let offerIds = this.offers.map(offer => offer.id);
        this.messageService.getAllConversationsForOffers(offerIds).subscribe(
          data => {
            this.conversationLists = data;
            this.setMap();
            this.loading = false;
          }
        );
      }
    )
  }

  getNewMessageCountForConversationList(list: Conversation[]) {
    return list
      .map(conv => conv.messages
        .map(mess => mess.wasReadByOfferOwner)
        .reduce((acc, n) => acc && n))
      .filter(m => !m)
      .length;
  }

  setMap() {
    for(let i = 0; i<this.offers.length; i++) {
      this.offerListMap.set(this.offers[i], this.conversationLists[i]);
    }
  }

  splitOffersToThreeInRow(): Offer[][] {
    let splitOffers = [];

    if(this.offerListMap) {
      for(let i = 0; i < this.offers.length; i = i + 3) {
        splitOffers.push(this.offers.slice(i, i+3));
      }
    }

    return splitOffers;
  }

  isLogged() {
    return this.authService.authenticated;
  }

  delete(id: number) {
    if(confirm("Are you sure you want to delete this offer?")) {
      this.offerService.deleteOffer(id).subscribe(
        data => this.fetchData()
      );
    }

  }

}
