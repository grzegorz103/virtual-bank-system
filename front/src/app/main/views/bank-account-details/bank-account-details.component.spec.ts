import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BankAccountDetailsComponent } from './bank-account-details.component';

describe('BankAccountDetailsComponent', () => {
  let component: BankAccountDetailsComponent;
  let fixture: ComponentFixture<BankAccountDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BankAccountDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BankAccountDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
