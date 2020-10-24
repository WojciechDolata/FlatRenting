import {Component} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {AuthService} from "./services/auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Flat Renting';


  constructor(private authService: AuthService, private http: HttpClient, private router: Router) {
    this.authService.authenticate(undefined, undefined, undefined);
  }

  logout() {
    this.authService.authenticated = false;
    localStorage.removeItem("authString");
    localStorage.removeItem("user");
    this.router.navigateByUrl('/@Dataarch');
  }

  isLogged(): boolean {
    return this.authService.authenticated;
  }

  getCurrentNick(): string {
    return this.authService.getNick();
  }

}
