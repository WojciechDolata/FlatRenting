import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {OfferSearchComponent} from "./components/offers/offer-search/offer-search.component";
import {AddOfferComponent} from "./components/offers/add-offer/add-offer.component";
import {OfferDetailsComponent} from "./components/offers/offer-details/offer-details.component";
import {LoginComponent} from "./components/auth/login/login.component";
import {RegisterComponent} from "./components/auth/register/register.component";
import {AllConversationsComponent} from "./components/messages/all-conversations/all-conversations.component";
import {SingleConversationComponent} from "./components/messages/single-conversation/single-conversation.component";

const routes: Routes = [
  { path: 'offers', component: OfferSearchComponent },
  { path: 'create-offer', component: AddOfferComponent },
  { path: 'offer-detail/:id', component: OfferDetailsComponent },
  { path: 'login', component: LoginComponent },
  { path: 'messages', component: AllConversationsComponent },
  { path: 'conversation', component: SingleConversationComponent },
  { path: 'register', component: RegisterComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
