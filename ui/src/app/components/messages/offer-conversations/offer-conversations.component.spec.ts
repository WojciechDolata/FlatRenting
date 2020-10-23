import {ComponentFixture, TestBed} from '@angular/core/testing';

import {OfferConversationsComponent} from './offer-conversations.component';

describe('OfferConversationsComponent', () => {
  let component: OfferConversationsComponent;
  let fixture: ComponentFixture<OfferConversationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OfferConversationsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OfferConversationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
