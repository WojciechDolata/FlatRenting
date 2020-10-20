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

  constructor(private authService: AuthService,
              private messageService: MessageService) { }

  ngOnInit(): void {
    this.fetchConversations();
  }

  getCurrentUser(): string {
    return this.authService.nick;
  }

  fetchConversations() {
    this.messageService.getConversations().subscribe(data => this.conversations = data);
  }

  setAsCurrentConversation(conversation: Conversation) {
    this.authService.currentConversation = conversation;
  }

  isLogged() {
    return this.authService.authenticated;
  }

  getLatestMessageDate(conversation: Conversation) {
    return DateUtils.formatDate(conversation.messages[conversation.messages.length - 1].creationTimestamp);
  }

}
