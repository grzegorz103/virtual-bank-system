import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TransactionMultiCurrencyComponent } from './transaction-multi-currency.component';

describe('TransactionMultiCurrencyComponent', () => {
  let component: TransactionMultiCurrencyComponent;
  let fixture: ComponentFixture<TransactionMultiCurrencyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TransactionMultiCurrencyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransactionMultiCurrencyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
