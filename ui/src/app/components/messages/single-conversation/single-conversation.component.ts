import {Component, OnInit} from '@angular/core';
import {Conversation} from "../../../models/models";
import {MessageService} from "../../../services/message.service";
import {AuthService} from "../../../services/auth.service";
import DateUtils from "../../../utils/date-utils";
import {ActivatedRoute} from "@angular/router";


@Component({
  selector: 'app-single-conversation',
  templateUrl: './single-conversation.component.html',
  styleUrls: ['./single-conversation.component.css']
})
export class SingleConversationComponent implements OnInit {

  conversation: Conversation;
  secondUser: string;
  isLoaded = false;

  constructor(
    private messageService: MessageService,
    private authService: AuthService,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.fetchData();
  }

  fetchData() {
    let id = +this.route.snapshot.paramMap.get('id');
    this.messageService.markRead(id).subscribe(
      conversation => {
        this.conversation = conversation;
        this.secondUser = this.conversation.user.nick == this.authService.getNick() ? this.conversation.offer.owner.nick : this.conversation.user.nick;
        this.isLoaded = true;
      }, () => alert("Something went wrong")
    );
  }

  updateConversationData(updatedConversation: Conversation) {
    updatedConversation.messages = updatedConversation.messages.sort(
      (m1, m2) => m1.id - m2.id
    );
    this.conversation = updatedConversation;
  }

  getLoggedUser() {
    return this.authService.getNick();
  }

  isLogged() {
    return this.authService.authenticated;
  }

  formatDate(date) {
    return DateUtils.formatDate(date);
  }

}
