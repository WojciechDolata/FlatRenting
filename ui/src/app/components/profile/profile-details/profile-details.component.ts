import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {AuthService} from "../../../services/auth.service";
import {Router} from "@angular/router";
import {User} from "../../../models/models";
import {UserService} from "../../../services/user.service";

@Component({
  selector: 'app-profile-details',
  templateUrl: './profile-details.component.html',
  styleUrls: ['./profile-details.component.css']
})
export class ProfileDetailsComponent implements OnInit {

  newUserForm: FormGroup;

  passMismatch: boolean;
  emailWrong: boolean;
  nickWrong: boolean;
  user: User;
  email: string;

  EMAIL_MATCHER: string = "[a-zA-Z0-9_\\.\\+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-\\.]+";

  constructor(private authService: AuthService,
              private formBuilder: FormBuilder,
              private userService: UserService,
              private router: Router) {
    this.initFormGroup();
  }

  private initFormGroup() {
    this.emailWrong = false;
    this.passMismatch = false;
    this.newUserForm = this.formBuilder.group({
      email: '',
      phoneNumber: ''
    });
  }

  ngOnInit(): void {
    this.fetchData();
  }

  fetchData() {
    this.userService.getEmail().subscribe(email => {

      console.log(email);
      this.email = email;
    });
    this.userService.getUser().subscribe(user => this.user = user);
  }

  validate(formValue): boolean {
    this.emailWrong = ! (String(formValue.email).match(this.EMAIL_MATCHER));
    this.passMismatch = formValue.password !== formValue.passwordConfirmation;

    this.nickWrong = ! (formValue.nick !== '' || formValue.nick.length > 5);
    return !this.passMismatch && !this.emailWrong && !this.nickWrong;
  }

  onSubmit(formValue) {
    if(this.validate(formValue)) {
      if(formValue.email != this.email) {
        this.userService.updateEmail(formValue.email).subscribe();
      }

      if(formValue.phoneNumber != this.user.phoneNumber) {
        this.userService.updatePhoneNumber(formValue.phoneNumber).subscribe();
      }
    }
  }

  isLogged() {
    return this.authService.authenticated;
  }
}
