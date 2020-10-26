import {Injectable} from '@angular/core';

import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Conversation, User} from "../models/models";
import * as CryptoJS from 'crypto-js';
import {Router} from "@angular/router";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})

export class AuthService {

  authenticated = false;
  currentConversation: Conversation = null;

  private baseUrl = environment.serverUrl;
  private loginUrl = this.baseUrl + "login";
  private registerUrl =  this.baseUrl + "register";

  constructor(private http: HttpClient,
              private router: Router) {
  }

  private static hashString(pass: string): string {
    return CryptoJS.SHA256(pass).toString(CryptoJS.enc.Hex)
  }

  getNick() {
    return localStorage.getItem("user");
  }

  getHeaders() {
    let authString = localStorage.getItem("authString")
    return new HttpHeaders(authString ? {authorization: authString} : {});
  }

  authenticate(credentials, callback, errorFunction) {
    if(credentials) {
      localStorage.setItem('authString', 'Basic ' + btoa(credentials.username + ':' + AuthService.hashString(credentials.password)));
      localStorage.setItem('user', credentials.username);
    }

    this.http.get(this.loginUrl, {headers: this.getHeaders()}).subscribe(response => {
      if(response) {
        if (response['name']) {
          this.authenticated = true;
        } else {
          this.authenticated = false;
        }
      }
      return callback && callback();
    }, error => errorFunction && errorFunction());

  }

  register(user: User) {
    var password = user.passwordHash;
    const data: FormData = new FormData();
    data.append('nick', user.nick);
    data.append('passwordHash', AuthService.hashString(user.passwordHash));
    data.append('phoneNumber', user.phoneNumber);
    data.append('email', user.email);

    this.http.post(this.registerUrl, data).subscribe(
      nextUser => {
        this.authenticate({username: user.nick, password: password}, () => {
          this.router.navigateByUrl('/offers');
        }, () => {});
      }
    );
  }

}
