import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {OfferSearchComponent} from "./components/offer-search/offer-search.component";

const routes: Routes = [
  { path: 'offers', component: OfferSearchComponent },
  // { path: 'second-component', component: SecondComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
