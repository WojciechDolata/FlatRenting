import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {OfferSearchComponent} from './components/offer-search/offer-search.component';
import {AddOfferComponent} from './components/add-offer/add-offer.component';

@NgModule({
  declarations: [
    AppComponent,
    OfferSearchComponent,
    AddOfferComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  entryComponents: [
    OfferSearchComponent
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
