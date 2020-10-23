import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../services/auth.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Router} from "@angular/router";
import {User} from "../../../models/models";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  newUserForm: FormGroup;

  passMismatch: boolean;
  emailWrong: boolean;
  nickWrong: boolean;

  EMAIL_MATCHER: string = "[a-zA-Z0-9_\\.\\+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-\\.]+";

  constructor(private authService: AuthService,
              private formBuilder: FormBuilder,
              private router: Router) {
    this.initFormGroup();
  }

  private initFormGroup() {
    this.emailWrong = false;
    this.passMismatch = false;
    this.newUserForm = this.formBuilder.group({
      nick: '',
      email: '',
      password: '',
      passwordConfirmation: '',
      phoneNumber: '',
      offers: [],
      conversations: []
    });
  }

  ngOnInit(): void {
  }

  validate(formValue): boolean {
    this.emailWrong = ! (String(formValue.email).match(this.EMAIL_MATCHER));
    this.passMismatch = formValue.password !== formValue.passwordConfirmation;

    this.nickWrong = ! (formValue.nick !== '' || formValue.nick.length > 5);
    return !this.passMismatch && !this.emailWrong && !this.nickWrong;
  }

  onSubmit(formValue) {
    if(this.validate(formValue)) {
      var user: User = {
        id: null,
        creationTimestamp: null,
        nick: formValue.nick,
        email: formValue.email,
        passwordHash: formValue.password,
        phoneNumber: formValue.phoneNumber,
        offers: [],
        conversations: []
      };

      this.authService.register(user);
    }
  }

}
