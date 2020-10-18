import {Injectable} from '@angular/core';

import {HttpClient, HttpHeaders} from '@angular/common/http';
import {User} from "../models/models";
import * as CryptoJS from 'crypto-js';
import {Router} from "@angular/router";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})

export class AuthService {

  authenticated = false;
  nick: string;
  headers: HttpHeaders;

  private baseUrl = environment.serverUrl;
  private loginUrl = this.baseUrl + "login";
  private registerUrl =  this.baseUrl + "register";

  constructor(private http: HttpClient,
              private router: Router) {
  }

  private hashString(pass: string): string {
    return CryptoJS.SHA256(pass).toString(CryptoJS.enc.Hex)
  }

  authenticate(credentials, callback, errorFunction) {
    this.headers = new HttpHeaders(credentials ? {
      authorization : 'Basic ' + btoa(credentials.username + ':' + this.hashString(credentials.password))
    } : {});

    this.http.get(this.loginUrl, {headers: this.headers}).subscribe(response => {
      if (response['name']) {
        this.authenticated = true;
        this.nick = credentials.username;
      } else {
        this.authenticated = false;
      }
      return callback && callback();
    }, error => errorFunction && errorFunction());

  }

  register(user: User) {
    var password = user.passwordHash;
    user.passwordHash = this.hashString(user.passwordHash);
    this.http.post(this.registerUrl, user).subscribe(
      nextUser => {
        this.authenticate({username: user.nick, password: password}, () => {
          this.router.navigateByUrl('/offer-search');
        }, () => {});
      }
    );
  }

}
