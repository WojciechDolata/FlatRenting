import {Component} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {AuthService} from "./services/auth.service";
import {finalize} from "rxjs/operators";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'ui';


  constructor(private authService: AuthService, private http: HttpClient, private router: Router) {
    this.authService.authenticate(undefined, undefined, undefined);
  }
  logout() {
    this.http.post('logout', {}).pipe(finalize(() => {
      this.authService.authenticated = false;
      this.router.navigateByUrl('/offer-search');
    })).subscribe();
  }

}
