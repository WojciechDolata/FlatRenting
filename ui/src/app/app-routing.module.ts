import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {OfferSearchComponent} from "./components/offers/offer-search/offer-search.component";
import {AddOfferComponent} from "./components/offers/add-offer/add-offer.component";
import {OfferDetailsComponent} from "./components/offers/offer-details/offer-details.component";
import {LoginComponent} from "./components/auth/login/login.component";
import {RegisterComponent} from "./components/auth/register/register.component";
import {AllConversationsComponent} from "./components/messages/all-conversations/all-conversations.component";
import {SingleConversationComponent} from "./components/messages/single-conversation/single-conversation.component";
import {ProfileDetailsComponent} from "./components/profile/profile-details/profile-details.component";
import {YourOffersComponent} from "./components/offers/your-offers/your-offers.component";
import {OfferConversationsComponent} from "./components/messages/offer-conversations/offer-conversations.component";
import {UserPreferencesComponent} from "./components/profile/user-preferences/user-preferences.component";
import {PreferredOffersComponent} from "./components/offers/preferred-offers/preferred-offers.component";

const routes: Routes = [
  { path: 'offers', component: OfferSearchComponent },
  { path: 'create-offer', component: AddOfferComponent },
  { path: 'offer-detail/:id', component: OfferDetailsComponent },
  { path: 'login', component: LoginComponent },
  { path: 'messages', component: AllConversationsComponent },
  { path: 'offer-messages/:id', component: OfferConversationsComponent },
  { path: 'conversation/:id', component: SingleConversationComponent },
  { path: 'profile', component: ProfileDetailsComponent },
  { path: 'your-offers', component: YourOffersComponent },
  { path: 'preferences', component: UserPreferencesComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'preferred-offers', component: PreferredOffersComponent },
  { path: '**', component: OfferSearchComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
