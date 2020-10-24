import {Component, OnInit} from '@angular/core';
import {Conversation} from "../../../models/models";
import {MessageService} from "../../../services/message.service";
import {AuthService} from "../../../services/auth.service";
import DateUtils from "../../../utils/date-utils";


@Component({
  selector: 'app-single-conversation',
  templateUrl: './single-conversation.component.html',
  styleUrls: ['./single-conversation.component.css']
})
export class SingleConversationComponent implements OnInit {

  conversation: Conversation;
  secondUser: string;
  constructor(
    private messageService: MessageService,
    private authService: AuthService
  ) { }

  ngOnInit() {
    this.conversation = this.authService.currentConversation;
    this.secondUser = this.conversation.user.nick == this.authService.getNick() ? this.conversation.offer.owner.nick : this.conversation.user.nick;
  }

  updateConversationData(updatedConversation: Conversation) {
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
