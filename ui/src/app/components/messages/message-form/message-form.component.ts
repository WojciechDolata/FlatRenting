import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {MessageService} from "../../../services/message.service";
import {AuthService} from "../../../services/auth.service";

@Component({
  selector: 'app-message-form',
  templateUrl: './message-form.component.html',
  styleUrls: ['./message-form.component.css']
})
export class MessageFormComponent implements OnInit {
  messageForm: FormGroup;

  @Input() offerId: number;
  @Input() conversationId: number;
  @Input() createNewConversation: boolean;


  constructor(private formBuilder: FormBuilder,
              private messageService: MessageService,
              private authService: AuthService) { }

  ngOnInit(): void {
    this.initFormGroup();
  }

  isLogged(): boolean {
    return this.authService.authenticated;
  }

  private initFormGroup() {
    this.messageForm = this.formBuilder.group({
      message: ''
    });
  }

  onSubmit(formValue) {
    if(this.createNewConversation) {
      this.messageService.addConversation(formValue.message, this.offerId).subscribe(
        conversation => console.log("message sent correctly")
      )
    } else {
      this.messageService.addMessage(formValue.message, this.conversationId).subscribe(
        conversation => console.log("message sent correctly")
      )
    }
  }


}
