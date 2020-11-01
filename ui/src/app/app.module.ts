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
import {MessageFormComponent} from './components/messages/message-form/message-form.component';
import {YourOffersComponent} from './components/offers/your-offers/your-offers.component';
import {ProfileDetailsComponent} from './components/profile/profile-details/profile-details.component';
import {OfferConversationsComponent} from './components/messages/offer-conversations/offer-conversations.component';
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FooterComponent} from './components/footer/footer.component';
import {UserPreferencesComponent} from './components/profile/user-preferences/user-preferences.component';
import {PreferredOffersComponent} from './components/offers/preferred-offers/preferred-offers.component';
import {NothingHereComponent} from './components/nothing-here/nothing-here.component';
import {AgmCoreModule} from "@agm/core";
import {GoogleMapsModule} from "@angular/google-maps";

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
    SingleConversationComponent,
    MessageFormComponent,
    YourOffersComponent,
    ProfileDetailsComponent,
    OfferConversationsComponent,
    FooterComponent,
    UserPreferencesComponent,
    PreferredOffersComponent,
    NothingHereComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatProgressSpinnerModule,
    GoogleMapsModule,
    AgmCoreModule.forRoot({
      apiKey: ''
    })
  ],
  entryComponents: [
    OfferSearchComponent
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
