import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../services/auth.service";
import {UserService} from "../../../services/user.service";
import {UserPreferences} from "../../../models/models";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Router} from "@angular/router";
import {cities} from "../../../../environments/cities";

@Component({
  selector: 'app-user-preferences',
  templateUrl: './user-preferences.component.html',
  styleUrls: ['./user-preferences.component.css']
})
export class UserPreferencesComponent implements OnInit {
  preferences: UserPreferences;
  preferencesForm: FormGroup;
  loading = true;

  constructor(private authService: AuthService,
              private userService: UserService,
              private router: Router,
              private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.initFormGroup();
    this.fetchData()
  }

  private initFormGroup() {
    this.preferencesForm = this.formBuilder.group({
      minPrice: '',
      maxPrice: '',
      maxSize: '',
      minSize: '',
      minNumberOfRooms: '',
      maxNumberOfRooms: '',
      location: '',
      maxDaysAgo: '',
    });
  }

  fetchData() {
    this.userService.getPreferences().subscribe(
      data => {
        this.preferences = data;
        this.preferencesForm = this.formBuilder.group({
          minPrice: data.minPrice,
          maxPrice: data.maxPrice,
          maxSize: data.maxSize,
          minSize: data.minSize,
          minNumberOfRooms: data.minNumberOfRooms,
          maxNumberOfRooms: data.maxNumberOfRooms,
          location: data.location,
          maxDaysAgo: data.maxDaysAgo,
        });
      }, () => null,
      () => this.loading = false
    );
  }

  onSubmit(formValue) {
    let newPreferences: UserPreferences = {
      minPrice: formValue.minPrice,
      maxPrice: formValue.maxPrice,
      maxSize: formValue.maxSize,
      minSize: formValue.minSize,
      minNumberOfRooms: formValue.minNumberOfRooms,
      maxNumberOfRooms: formValue.maxNumberOfRooms,
      location: formValue.location,
      maxDaysAgo: formValue.maxDaysAgo,
      nick: this.authService.getNick(),
      creationTimestamp: this.preferences ? this.preferences.creationTimestamp : null,
      id: this.preferences ? this.preferences.id : null
    };

    this.userService.updatePreferences(newPreferences).subscribe(
      data => this.router.navigateByUrl("/offers")
    );
  }

  getCities() {
    return cities;
  }

  isLogged() {
    return this.authService.authenticated;
  }

}
