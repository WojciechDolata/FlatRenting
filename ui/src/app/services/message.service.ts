import {Injectable} from '@angular/core';
import {AuthService} from "./auth.service";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  private baseUrl = environment.serverUrl;

  constructor(private authService: AuthService) {

  }

}
