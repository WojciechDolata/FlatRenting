import {ComponentFixture, TestBed} from '@angular/core/testing';

import {PreferredOffersComponent} from './preferred-offers.component';

describe('PreferredOffersComponent', () => {
  let component: PreferredOffersComponent;
  let fixture: ComponentFixture<PreferredOffersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PreferredOffersComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PreferredOffersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
