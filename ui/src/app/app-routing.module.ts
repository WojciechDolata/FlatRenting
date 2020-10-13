import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {OfferSearchComponent} from "./components/offer-search/offer-search.component";
import {AddOfferComponent} from "./components/add-offer/add-offer.component";
import {OfferDetailsComponent} from "./components/offer-details/offer-details.component";

const routes: Routes = [
  { path: 'offers', component: OfferSearchComponent },
  { path: 'create-offer', component: AddOfferComponent },
  { path: 'offer-detail/:id', component: OfferDetailsComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
