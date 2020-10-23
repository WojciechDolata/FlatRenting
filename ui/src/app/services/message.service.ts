import {Injectable} from '@angular/core';
import {AuthService} from "./auth.service";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {Conversation, Message} from "../models/models";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  private baseUrl = environment.serverUrl;
  private messageUrl = this.baseUrl + 'message';

  constructor(private authService: AuthService,
              private http: HttpClient) {
  }

  getConversationsByNick(): Observable<Conversation[]> {
    return this.http.get<Conversation[]>(
      this.messageUrl + "/allByNick/" + this.authService.nick,
      {headers: this.authService.headers}
      );
  }

  getAllConversationsForOffers(offerIds): Observable<Conversation[][]> {
    const data: FormData = new FormData();
    data.append('offerIds', offerIds);
    return this.http.post<Conversation[][]>(
      this.messageUrl + "/allByOffers",
      data,
      {headers: this.authService.headers}
    );
  }


  addConversation(message: string, offerId: number) {
    const data: FormData = new FormData();
    data.append('message', message);
    data.append('userNick', this.authService.nick);
    data.append('offerId', offerId.toString());

    return this.http.post<Conversation>(
      this.messageUrl + "/addConversation",
      data,
      {headers: this.authService.headers}
    )
  }

  addMessage(message: string, conversationId: number) {
    const data: FormData = new FormData();
    data.append('message', message);
    data.append('userNick', this.authService.nick);
    data.append('conversationId', conversationId.toString());

    return this.http.post<Conversation>(
      this.messageUrl + "/addMessage",
      data,
      {headers: this.authService.headers}
    )
  }

  getLastMessage(conversationId): Observable<Message> {
    return this.http.get<Message>(
      this.messageUrl + "/getLastMessage/" + conversationId,
      {headers: this.authService.headers}
    )
  }


}
