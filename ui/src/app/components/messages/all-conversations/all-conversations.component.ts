import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../services/auth.service";
import {Conversation} from "../../../models/models";
import {MessageService} from "../../../services/message.service";
import DateUtils from "../../../utils/date-utils";

@Component({
  selector: 'app-all-conversations',
  templateUrl: './all-conversations.component.html',
  styleUrls: ['./all-conversations.component.css']
})
export class AllConversationsComponent implements OnInit {

  conversations: Conversation[];
  conversationNewMessageMap: Map<Conversation, number> = new Map();
  loading = true;

  constructor(private authService: AuthService,
              private messageService: MessageService) { }

  ngOnInit(): void {
    if (this.isLogged())
      this.fetchConversations();
  }

  getCurrentUser(): string {
    return this.authService.getNick();
  }

  fetchConversations() {
    this.messageService.getConversationsByNick().subscribe(data => {
      data.forEach(conv => this.conversationNewMessageMap.set(conv, this.getNewMessageCount(conv)))
    }, () => alert("Something went wrong"),
      () => this.loading = false);
  }

  getNewMessageCount(conversation: Conversation) {
    return conversation.messages.filter(message => !message.wasReadBySecondUser).length;
  }

  isLogged() {
    return this.authService.authenticated;
  }

  getLatestMessageDate(conversation: Conversation) {
    return DateUtils.formatDate(conversation.messages[conversation.messages.length - 1].creationTimestamp);
  }

}
