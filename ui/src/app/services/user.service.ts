import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../models/models";
import {environment} from "../../environments/environment";
import {AuthService} from "./auth.service";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = environment.serverUrl;
  private userUrl = this.baseUrl + "user";

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) { }

  getEmail(): Observable<string> {
    const data = new FormData();
    data.append('nick', this.authService.nick);
    return this.http.post<string>(
      this.userUrl + "/getEmail",
      data,
      {headers: this.authService.headers}
    );
  }

  updatePassword(passwordHash): Observable<string> {
    const data = new FormData();
    data.append('nick', this.authService.nick);
    data.append('passwordHash', passwordHash);
    return this.http.post<string>(
      this.userUrl + "/updatePassword",
      data,
      {headers: this.authService.headers}
    );
  }

  updateEmail(email): Observable<string> {
    const data = new FormData();
    data.append('nick', this.authService.nick);
    data.append('email', email);
    return this.http.post<string>(
      this.userUrl + "/updateEmail",
      data,
      {headers: this.authService.headers}
    );
  }

  updatePhoneNumber(phoneNumber): Observable<string> {
    const data = new FormData();
    data.append('nick', this.authService.nick);
    data.append('phoneNumber', phoneNumber);
    return this.http.post<string>(
      this.userUrl + "/updatePhoneNumber",
      data,
      {headers: this.authService.headers}
    );
  }

  getUser(): Observable<User> {
    return this.http.get<User>(
      this.userUrl + "/byNick/" + this.authService.nick,
      {headers: this.authService.headers}
    );
  }
}
