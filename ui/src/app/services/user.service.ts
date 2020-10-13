import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Offer, User} from "../models/models";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userUrl = "http://localhost:8080/user";

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
