import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {OfferSearchComponent} from "./components/offer-search/offer-search.component";
import {AddOfferComponent} from "./components/add-offer/add-offer.component";

const routes: Routes = [
  { path: 'offers', component: OfferSearchComponent },
  { path: 'create-offer', component: AddOfferComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
