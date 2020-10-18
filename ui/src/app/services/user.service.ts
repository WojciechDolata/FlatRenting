import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Offer, User} from "../models/models";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = environment.serverUrl;
  private userUrl = this.baseUrl + "user";

  constructor(
    private http: HttpClient
  ) { }

  getUser(id: number): Observable<Offer> {
    return this.http.get<Offer>(this.userUrl + "/" + id);
  }

  addNewUser(user: User): Observable<User> {
    return this.http.post<User>(this.userUrl + "/add", user);
  }
}
