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
  conversationLists: Conversation[][];

  constructor(private authService: AuthService,
              private offerService: OfferService,
              private messageService: MessageService
  ) { }

  ngOnInit(): void {
    this.fetchData();
  }

  fetchData() {
    this.offerService.getAllForUser(this.authService.nick).subscribe(
      data => {
        this.offers = data;
        let offerIds = this.offers.map(offer => offer.id);
        this.messageService.getAllConversationsForOffers(offerIds).subscribe(
          data => {
            this.conversationLists = data;
            console.log(data);
          }
        );
      }
    )


  }

  isLogged() {
    return this.authService.authenticated;
  }

}
