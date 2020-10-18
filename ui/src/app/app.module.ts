import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {OfferSearchComponent} from './components/offers/offer-search/offer-search.component';
import {AddOfferComponent} from './components/offers/add-offer/add-offer.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {OfferDetailsComponent} from './components/offers/offer-details/offer-details.component';
import {OfferNotFoundComponent} from './components/error/offer-not-found/offer-not-found.component';
import {LoginComponent} from './components/auth/login/login.component';
import {RegisterComponent} from './components/auth/register/register.component';
import {AuthFailedComponent} from './components/error/auth-failed/auth-failed.component';
import {AllConversationsComponent} from './components/messages/all-conversations/all-conversations.component';
import {SingleConversationComponent} from './components/messages/single-conversation/single-conversation.component';

@NgModule({
  declarations: [
    AppComponent,
    OfferSearchComponent,
    AddOfferComponent,
    OfferDetailsComponent,
    OfferNotFoundComponent,
    LoginComponent,
    RegisterComponent,
    AuthFailedComponent,
    AllConversationsComponent,
    SingleConversationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  entryComponents: [
    OfferSearchComponent
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
